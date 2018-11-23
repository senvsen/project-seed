package com.yupaits.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author yupaits
 * @date 2018/11/8
 */
@Configuration
@EnableScheduling
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobFactory;
    private final StepBuilderFactory stepFactory;
    private final FetchHistoryDataTask fetchHistoryDataTask;
    private final MigrateHistoryDataTask migrateHistoryDataTask;
    private final JobLauncher jobLauncher;

    private static final String JOB_ID = "JobID";
    private static final String BATCH_JOB_ID_KEY = "batch:job:id";
    private RedisAtomicLong batchJobId;

    @Autowired
    public BatchConfig(JobBuilderFactory jobFactory, StepBuilderFactory stepFactory,
                       FetchHistoryDataTask fetchHistoryDataStep, MigrateHistoryDataTask migrateHistoryDataStep,
                       JobLauncher jobLauncher, RedisConnectionFactory redisConnectionFactory) {
        this.jobFactory = jobFactory;
        this.stepFactory = stepFactory;
        this.fetchHistoryDataTask = fetchHistoryDataStep;
        this.migrateHistoryDataTask = migrateHistoryDataStep;
        this.jobLauncher = jobLauncher;
        this.batchJobId = new RedisAtomicLong(BATCH_JOB_ID_KEY, redisConnectionFactory);
    }

    @Bean
    public Step fetchHistoryData() {
        return stepFactory.get("fetchHistoryData")
                .tasklet(fetchHistoryDataTask)
                .build();
    }

    @Bean
    public Step migrateHistoryData() {
        return stepFactory.get("migrateHistoryData")
                .tasklet(migrateHistoryDataTask)
                .build();
    }

    @Bean
    public Job historyDataMigrationJob() {
        return jobFactory.get("historyDataMigrationJob")
                .incrementer(new RunIdIncrementer())
                .start(fetchHistoryData())
                .next(migrateHistoryData())
                .build();

    }

    @Scheduled(cron = "0 30 * * * ?")
    public void runHistoryDataMigration() throws JobExecutionException {
        JobParameters params = new JobParametersBuilder()
                .addLong(JOB_ID, this.batchJobId.incrementAndGet())
                .toJobParameters();
        jobLauncher.run(historyDataMigrationJob(), params);
    }
}
