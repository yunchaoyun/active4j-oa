package com.active4j.hr.func.timer.config;

import javax.sql.DataSource;

import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * 
 * @title QuartzConfig.java
 * @description 
		定时任务配置
 * @time  2019年12月9日 上午11:32:59
 * @author guyp
 * @version 1.0
 */
@Configuration
@EnableScheduling
public class QuartzConfig {

	/**
     * 继承org.springframework.scheduling.quartz.SpringBeanJobFactory
     * 实现任务实例化方式
     */
    public static class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

        private transient AutowireCapableBeanFactory beanFactory;

        @Override
        public void setApplicationContext(final ApplicationContext context) {
            beanFactory = context.getAutowireCapableBeanFactory();
        }

        /**
         * 将job实例交给spring ioc托管
         * 我们在job实例实现类内可以直接使用spring注入的调用被spring ioc管理的实例
         *
         * @param bundle
         * @return
         * @throws Exception
         */
        @Override
        protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
            final Object job = super.createJobInstance(bundle);
            //将job实例交付给spring ioc
            beanFactory.autowireBean(job);
            return job;
        }
    }

    /**
     * 配置任务工厂实例
     *
     * @return
     */
    @Bean
    public JobFactory jobFactory() {
        /**
         * 采用自定义任务工厂 整合spring实例来完成构建任务*/
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        return jobFactory;
    }

    /**
     * 配置任务调度器
     * 使用项目数据源作为quartz数据源
     *
     * @param jobFactory 自定义配置任务工厂
     * @param dataSource 数据源实例
     * @return
     * @throws Exception
     */
    @Bean(destroyMethod = "destroy", autowire = Autowire.NO)
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, DataSource dataSource) throws Exception {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //将spring管理job自定义工厂交由调度器维护
        schedulerFactoryBean.setJobFactory(jobFactory);
        //设置覆盖已存在的任务，用于集群配置
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //项目启动完成后，等待60秒后开始执行调度器初始化，避免系统未完全启动却开始执行定时任务的情况
        schedulerFactoryBean.setStartupDelay(60);
        //设置调度器自动运行
        schedulerFactoryBean.setAutoStartup(true);
        //设置数据源，使用与项目统一数据源
        schedulerFactoryBean.setDataSource(dataSource);
        //设置上下文spring bean name
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        //设置配置文件位置
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("/quartz.properties"));
        return schedulerFactoryBean;
    }
}
