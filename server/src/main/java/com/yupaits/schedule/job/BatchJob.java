package com.yupaits.schedule.job;

import com.yupaits.batch.config.BatchConfig;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yupaits
 * @date 2018/11/27
 */
@Component
public class BatchJob implements Job {

    private final BatchConfig batchConfig;

    @Autowired
    public BatchJob(BatchConfig batchConfig) {
        this.batchConfig = batchConfig;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        batchConfig.runHistoryDataMigration();
    }
}
