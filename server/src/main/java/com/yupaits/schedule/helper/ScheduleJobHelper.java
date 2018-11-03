package com.yupaits.schedule.helper;

import com.yupaits.commons.exceptions.ServiceException;
import com.yupaits.schedule.entity.Job;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

/**
 * @author yupaits
 * @date 2018/11/3
 */
@Slf4j
public class ScheduleJobHelper {

    /**
     * 获取JobKey
     */
    public static JobKey getJobKey(Job job) {
        return JobKey.jobKey(job.getJobName(), job.getJobGroup());
    }

    /**
     * 获取TriggerKey
     */
    public static TriggerKey getTriggerKey(Job job) {
        return TriggerKey.triggerKey(job.getTriggerName(), job.getTriggerGroup());
    }

    /**
     * 获取CronTrigger
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Job job) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(job));
        } catch (SchedulerException e) {
            throw new ServiceException("获取cron触发器失败", e);
        }
    }

    /**
     * 创建任务
     */
    public static void createScheduleJob(Scheduler scheduler, Job job) {
        validateCronExpression(job);
        try {
            //noinspection unchecked
            Class<? extends org.quartz.Job> jobClass = (Class<? extends org.quartz.Job>) Class.forName(job.getClassName());
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(job.getJobName(), job.getJobGroup())
                    .withDescription(job.getDescription())
                    .build();
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(job.getTriggerName(), job.getTriggerGroup())
                    .withDescription(job.getDescription())
                    .withSchedule(scheduleBuilder)
                    .startNow()
                    .build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
            if (job.isPaused()) {
                pauseJob(scheduler, job);
            }
        } catch (ClassNotFoundException | SchedulerException e) {
            log.error("创建定时任务失败, 定时任务: {}", e);
            throw new ServiceException("创建定时任务失败");
        }
    }

    /**
     * 更新任务
     */
    public static void updateScheduleJob(Scheduler scheduler, Job job) {
        validateCronExpression(job);
        try {
            TriggerKey triggerKey = getTriggerKey(job);
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger = getCronTrigger(scheduler, job);
            if (cronTrigger != null) {
                cronTrigger.getTriggerBuilder()
                        .withIdentity(triggerKey)
                        .withDescription(job.getDescription())
                        .withSchedule(cronScheduleBuilder)
                        .build();
                scheduler.rescheduleJob(triggerKey, cronTrigger);
                if (job.isPaused()) {
                    pauseJob(scheduler, job);
                }
            } else {
                log.warn("定时任务触发器不存在, 触发器: {}", getTriggerKey(job));
            }
        } catch (SchedulerException e) {
            log.error("更新定时任务失败, 定时任务: {}", job, e);
            throw new ServiceException("更新定时任务失败");
        }
    }

    /**
     * 运行任务
     */
    public static void run(Scheduler scheduler, Job job) {
        try {
            scheduler.triggerJob(getJobKey(job));
        } catch (SchedulerException e) {
            log.error("运行定时任务失败, 定时任务: {}", job, e);
            throw new ServiceException("运行定时任务失败");
        }
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Scheduler scheduler, Job job) {
        try {
            scheduler.pauseJob(getJobKey(job));
        } catch (SchedulerException e) {
            log.error("暂停定时任务失败, 定时任务: {}", job, e);
            throw new ServiceException("暂停定时任务失败");
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, Job job) {
        try {
            scheduler.resumeJob(getJobKey(job));
        } catch (SchedulerException e) {
            log.error("恢复定时任务失败, 定时任务: {}", job, e);
            throw new ServiceException("恢复定时任务失败");
        }
    }

    /**
     * 删除任务
     */
    public static void deleteJob(Scheduler scheduler, Job job) {
        try {
            scheduler.deleteJob(getJobKey(job));
        } catch (SchedulerException e) {
            log.error("删除定时任务失败, 定时任务: {}", job, e);
            throw new ServiceException("删除定时任务失败");
        }
    }

    /**
     * 校验Cron表达式
     */
    public static void validateCronExpression(Job job) {
        if (!CronExpression.isValidExpression(job.getCronExpression())) {
            throw new ServiceException(String.format("Job[%s] cron[%s]表达式有误！", job.getClassName(), job.getCronExpression()));
        }
    }
}
