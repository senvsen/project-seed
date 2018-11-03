package com.yupaits.schedule.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupaits.schedule.entity.Job;
import com.yupaits.schedule.helper.ScheduleJobHelper;
import com.yupaits.schedule.service.IJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yupaits
 * @date 2018/11/3
 */
@Slf4j
@Component
public class ScheduleListener implements CommandLineRunner {

    private final IJobService jobService;
    private final Scheduler scheduler;

    @Autowired
    public ScheduleListener(IJobService jobService, Scheduler quartzScheduler) {
        this.jobService = jobService;
        this.scheduler = quartzScheduler;
    }

    @Override
    public void run(String... args) {
        List<Job> jobList = jobService.list(new QueryWrapper<>());
        jobList.forEach(job -> {
            CronTrigger cronTrigger = ScheduleJobHelper.getCronTrigger(scheduler, job);
            if (cronTrigger == null) {
                ScheduleJobHelper.createScheduleJob(scheduler, job);
            } else {
                ScheduleJobHelper.updateScheduleJob(scheduler, job);
            }
            log.info("定时任务[{} - {}]启动成功", job.getJobGroup(), job.getJobName());
        });
    }
}
