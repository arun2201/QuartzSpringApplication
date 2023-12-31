package com.QuartzSpringApplication.QuartzSpringApplication.config;

import com.QuartzSpringApplication.QuartzSpringApplication.job.LoadBookJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuartzConfig {
    final ApplicationContext applicationContext;
    private SpringBeanJobFactory springBeanJobFactory;
    private Trigger trigger;

    public QuartzConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    SpringBeanJobFactory createSpringBeanJobFactory (){

        return new SpringBeanJobFactory() {

            @Override
            protected Object createJobInstance(final TriggerFiredBundle bundle)
                    throws Exception {

                final Object job = super.createJobInstance(bundle);

                applicationContext
                        .getAutowireCapableBeanFactory()
                        .autowireBean(job);

                return job;
            }
        };
    }
    @Bean
    public SchedulerFactoryBean
    createSchedulerFactory(SpringBeanJobFactory springBeanJobFactory, Trigger trigger) {
        this.springBeanJobFactory = springBeanJobFactory;
        this.trigger = trigger;

        SchedulerFactoryBean schedulerFactory
                = new SchedulerFactoryBean();
        schedulerFactory.setAutoStartup(true);
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
        schedulerFactory.setTriggers(trigger);

        springBeanJobFactory.setApplicationContext(applicationContext);
        schedulerFactory.setJobFactory(springBeanJobFactory);

        return schedulerFactory;
    }
    @Bean
    public SimpleTriggerFactoryBean createSimpleTriggerFactoryBean(JobDetail jobDetail)
    {
        SimpleTriggerFactoryBean simpleTriggerFactory
                = new SimpleTriggerFactoryBean();

        simpleTriggerFactory.setJobDetail(jobDetail);
        simpleTriggerFactory.setStartDelay(0);
        simpleTriggerFactory.setRepeatInterval(5000);
        simpleTriggerFactory.setRepeatCount(10);
        return simpleTriggerFactory;
    }
    @Bean
    public JobDetailFactoryBean createJobDetailFactoryBean(){

        JobDetailFactoryBean jobDetailFactory
                = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(LoadBookJob.class);
        return jobDetailFactory;
    }
}
