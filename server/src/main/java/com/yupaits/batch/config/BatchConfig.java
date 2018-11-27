package com.yupaits.batch.config;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.yupaits.batch.consts.MigrationDataConsts;
import com.yupaits.batch.listener.JobCompletedListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author yupaits
 * @date 2018/11/8
 */
@Slf4j
@Component
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobFactory;
    private final StepBuilderFactory stepFactory;
    private final JobLauncher jobLauncher;
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private final JobCompletedListener jobCompletedListener;

    public static final String JOB_ID = "JobId";
    private static final String BATCH_JOB_ID_KEY = "batch:job:id";
    private RedisAtomicLong batchJobId;

    private Map<String, Step> stepMap = Maps.newHashMap();
    private Map<String, Job> jobMap = Maps.newHashMap();

    private static final String JOB_SUFFIX = "_job";
    private static final String LINE_DELIMITER = " ||| ";

    private ExecutorService batchJobThreadPool;

    @Autowired
    public BatchConfig(JobBuilderFactory jobFactory, StepBuilderFactory stepFactory, JobLauncher jobLauncher,
                       DataSource dataSource, JdbcTemplate jdbcTemplate, JobCompletedListener jobCompletedListener,
                       RedisConnectionFactory redisConnectionFactory) {
        this.jobFactory = jobFactory;
        this.stepFactory = stepFactory;
        this.jobLauncher = jobLauncher;
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.jobCompletedListener = jobCompletedListener;
        this.batchJobId = new RedisAtomicLong(BATCH_JOB_ID_KEY, redisConnectionFactory);
    }

    public void runHistoryDataMigration() {
        log.info("开始清理历史数据");
        if (batchJobThreadPool == null || batchJobThreadPool.isShutdown()) {
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                    .setNameFormat("batch-job-pool-%d").build();
            batchJobThreadPool = new ThreadPoolExecutor(5, 20,
                    0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024),
                    namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        }
        long jobId = this.batchJobId.incrementAndGet();
        for (String entity : MigrationDataConsts.ENTITIES) {
            batchJobThreadPool.execute(() -> {
                try {
                    this.buildAndRunJob(entity, jobId);
                } catch (JobExecutionException e) {
                    log.error("执行历史数据清理任务[{}]出错", entity + JOB_SUFFIX + jobId, e);
                }
            });
        }
        batchJobThreadPool.shutdown();
    }

    /**
     * 构建并运行任务
     * @param entity 任务实体名
     * @param jobId  任务ID
     * @throws JobExecutionException 抛出JobExecutionException
     */
    private void buildAndRunJob(String entity, long jobId) throws JobExecutionException {
        JobParameters params = new JobParametersBuilder()
                .addString(JOB_ID, entity + jobId)
                .toJobParameters();
        Step step;
        if (stepMap.containsKey(entity) && stepMap.get(entity) != null) {
            step = stepMap.get(entity);
        } else {
            step = stepFactory.get(entity)
                    .chunk(1000)
                    .reader(buildItemReader(entity))
                    .writer(buildItemWriter(entity))
                    .processor(buildItemProcessor(entity))
                    .build();
            stepMap.put(entity, step);
        }
        Job job;
        if (jobMap.containsKey(entity) && jobMap.get(entity) != null) {
            job = jobMap.get(entity);
        } else {
            job = jobFactory.get(entity)
                    .listener(jobCompletedListener)
                    .start(step)
                    .build();
            jobMap.put(entity, job);
        }
        jobLauncher.run(job, params);
    }

    /**
     * 构建ItemReader
     * @param entity 实体
     * @return ItemReader
     */
    @SuppressWarnings("unchecked")
    private ItemReader<?> buildItemReader(String entity) {
        return new JdbcCursorItemReaderBuilder()
                .name(entity)
                .dataSource(dataSource)
                .sql(MigrationDataConsts.READER_SQL_MAP.get(entity))
                .rowMapper(new BeanPropertyRowMapper<>(MigrationDataConsts.READER_ROW_BEAN_MAP.get(entity)))
                .fetchSize(1000)
                .build();
    }

    /**
     * 构建ItemWriter
     * @param entity 实体
     * @return ItemWriter
     */
    @SuppressWarnings("unchecked")
    private ItemWriter<? super Object> buildItemWriter(String entity) {
        DelimitedLineAggregator lineAggregator = new DelimitedLineAggregator();
        lineAggregator.setDelimiter(LINE_DELIMITER);
        BeanWrapperFieldExtractor fieldExtractor = new BeanWrapperFieldExtractor();
        fieldExtractor.setNames(MigrationDataConsts.WRITER_FIELD_NAMES_MAP.get(entity));
        lineAggregator.setFieldExtractor(fieldExtractor);
        return new FlatFileItemWriterBuilder()
                .name(entity)
                .resource(new PathResource("/backup/" + entity + ".bak"))
                .shouldDeleteIfExists(false)
                .append(true)
                .lineAggregator(lineAggregator)
                .build();
    }

    /**
     * 构建ItemProcessor
     * @param entity 实体
     * @return ItemProcessor
     */
    private ItemProcessor<? super Object, ?> buildItemProcessor(String entity) {
        String preparedStatement = "delete from " + MigrationDataConsts.ENTITY_TABLE_MAP.get(entity) + " where id = ?";
        return (ItemProcessor<Object, Object>) item -> {
            Field idField = MigrationDataConsts.READER_ROW_BEAN_MAP.get(entity).getDeclaredField("id");
            idField.setAccessible(true);
            Long id = (Long) idField.get(item);
            int count = jdbcTemplate.update(preparedStatement, id);
            if (count > 0) {
                return item;
            } else {
                return null;
            }
        };
    }

}
