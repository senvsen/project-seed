package com.yupaits.schedule;

import com.google.common.collect.Lists;
import com.yupaits.schedule.entity.Job;
import com.yupaits.schedule.service.IJobService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yupaits
 * @date 2018/11/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class ScheduleTest {

    @Autowired
    private IJobService jobService;

    @Test
    public void testAddScheduleJob() {
        Assert.assertTrue(jobService.saveBatch(Lists.newArrayList(
                new Job().setClassName("com.yupaits.schedule.job.TestJob")
                        .setJobName("GoodJob")
                        .setJobGroup("LuckyGroup")
                        .setTriggerName("EveryHour")
                        .setTriggerGroup("Daily")
                        .setCronExpression("0 0/1 * * * ?")
                        .setDescription("测试定时任务"),
                new Job().setClassName("com.yupaits.schedule.job.SystemNoticeCleanJob")
                        .setJobName("SystemNoticeClean")
                        .setJobGroup("Cleaner")
                        .setTriggerName("EveryDayAt13")
                        .setTriggerGroup("Daily")
                        .setCronExpression("0 0 13 * * ?")
                        .setDescription("清理过期的系统通知"),
                new Job().setClassName("com.yupaits.schedule.job.BatchCleanJob")
                        .setJobName("HistoryDataClean")
                        .setJobGroup("Cleaner")
                        .setTriggerName("EveryHourAtHalf")
                        .setTriggerGroup("Daily")
                        .setCronExpression("0 30 * * * ?")
                        .setDescription("历史数据清理任务")
        )));
    }
}
