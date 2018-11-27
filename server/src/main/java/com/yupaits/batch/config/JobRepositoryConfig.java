package com.yupaits.batch.config;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * 解决BATCH_JOB_INSTANCE表死锁问题
 * @author yupaits
 * @date 2018/11/27
 */
@Configuration
public class JobRepositoryConfig {
    private static final String ISOLATION_REPEATABLE_READ = "ISOLATION_REPEATABLE_READ";

    @Bean
    public JobRepositoryFactoryBean jobRepositoryFactoryBean(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
        jobRepositoryFactoryBean.setDataSource(dataSource);
        jobRepositoryFactoryBean.setIsolationLevelForCreate(ISOLATION_REPEATABLE_READ);
        jobRepositoryFactoryBean.afterPropertiesSet();
        return jobRepositoryFactoryBean;
    }

    @Bean
    public JobRepository jobRepository(JobRepositoryFactoryBean factory) throws Exception {
        return factory.getObject();
    }
}
