//package com.fubon.ecplatformapi;
//
//import com.fubon.ecplatformapi.properties.CleanUpProperties;
//import org.quartz.JobDataMap;
//import org.quartz.JobDetail;
//import org.quartz.Trigger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
//import org.springframework.scheduling.quartz.JobDetailFactoryBean;
//import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//
//
//@Configuration
//public class QuartzConfiguration {
//    @Autowired
//    CleanUpProperties cleanUpProperties;
//
//    @Bean
//    public SchedulerFactoryBean schedulerFactory(JobDetail cleanJob, Trigger... triggers) {
//        SchedulerFactoryBean bean = new SchedulerFactoryBean();
//        bean.setOverwriteExistingJobs(true);
//        bean.setStartupDelay(1);
//        bean.setTriggers(triggers);
//        bean.setJobDetails(cleanJob);
//        return bean;
//    }
//
//    @Bean(name = "cleanJob")
//    public MethodInvokingJobDetailFactoryBean jobDetail(CleanUpJob task) {
//        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
//        jobDetail.setConcurrent(false);
//        jobDetail.setTargetObject(task);
//        jobDetail.setTargetMethod("fileCleanupTasklet");  // 對應程式要執行的 method
//        return jobDetail;
//    }
//
//    @Bean
//    public CronTriggerFactoryBean jobTrigger(JobDetail jobDetail) {
//        String cron = "*/5 * * * * ?"; // 排程設定
//        //cleanUpProperties.getCronExpression();
//        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
//        trigger.setJobDetail(jobDetail);
//        trigger.setCronExpression(cron);
//        return trigger;
//    }
//}
