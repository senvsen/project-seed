package com.yupaits.batch.listener;

import com.yupaits.batch.config.BatchConfig;
import com.yupaits.batch.consts.MigrationDataConsts;
import com.yupaits.commons.utils.aop.CacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yupaits
 * @date 2018/11/26
 */
@Slf4j
@Component
public class JobCompletedListener extends JobExecutionListenerSupport {

    private final RedisTemplate redisTemplate;

    @Autowired
    public JobCompletedListener(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobId = jobExecution.getJobParameters().getString(BatchConfig.JOB_ID);
        redisTemplate.delete(CacheUtils.genCacheStoreKey(MigrationDataConsts.ENTITY_CONTROLLER_MAP
                .getOrDefault(jobExecution.getJobInstance().getJobName(), Void.class).getCanonicalName()));
        log.info("历史数据清理任务[{}]执行完成", jobId);
    }
}
