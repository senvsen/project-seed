package com.yupaits.schedule.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

/**
 * @author yupaits
 * @date 2018/11/3
 */
@Configuration
public class ScheduleConfig {

    private static final String SCHEDULER_NAME = "";
    private static final int STARTUP_DELAY_SECONDS = 3;

    private final ScheduleJobFactory scheduleJobFactory;
    private final DataSource dataSource;

    @Autowired
    public ScheduleConfig(ScheduleJobFactory scheduleJobFactory, DataSource dataSource) {
        this.scheduleJobFactory = scheduleJobFactory;
        this.dataSource = dataSource;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setSchedulerName(SCHEDULER_NAME);
        schedulerFactoryBean.setStartupDelay(STARTUP_DELAY_SECONDS);
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setJobFactory(scheduleJobFactory);
        return schedulerFactoryBean;
    }
}
