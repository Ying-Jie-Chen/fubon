//package com.fubon.ecplatformapi;
//
//import com.fubon.ecplatformapi.properties.CleanUpProperties;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.core.step.tasklet.TaskletStep;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.File;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
//import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//
//import java.text.SimpleDateFormat;
//import java.time.Duration;
//import java.util.Date;
//import java.util.Objects;
//@Slf4j
//@Configuration
//@EnableBatchProcessing
//public class NasCleanUpJob {
//    @Autowired
//    CleanUpProperties cleanUpProperties;
//    @Autowired
//    JobBuilderFactory jobBuilderFactory;
//    @Autowired
//    StepBuilderFactory stepBuilderFactory;
//
//
//    /**
//     * 影像清理
//     *
//     */
//    @Bean
//    @Scheduled(fixedRateString = "${scheduling.fixedRate}")
//    public Tasklet fileCleanupTasklet(String path) {
//        return (stepContribution, chunkContext) -> {
//
//            String filePath = "${path}\\EcPlatForm\\" + path;
//
//            Duration expirationDays = cleanUpProperties.getExpirationDays();
//            log.info("Expire days: " + expirationDays.toDays());
//
//            File nasDirectory = new File(filePath);
//            File[] files = nasDirectory.listFiles();
//
//            if (files != null) {
//                for (File file : files) {
//                    if (isExpired(file, expirationDays)) {
//                        if (file.delete()) {
//                            log.info("刪除過期的檔案: " + file.getName());
//                            log.info("Path: " + file.getAbsolutePath());
//                        } else {
//                            log.info("沒有過期的檔案: " + file.getName());
//                            log.info("Path: " + file.getAbsolutePath());
//                        }
//                    }
//                }
//            }
//            return null;
//        };
//    }
//
//    @Bean
//    public Job fileCleanupJob(Tasklet fileCleanupTasklet) {
//        TaskletStep step = stepBuilderFactory.get("fileCleanupStep")
//                .tasklet(fileCleanupTasklet)
//                .build();
//        return jobBuilderFactory.get("fileCleanupJob")
//                .start(step)
//                .build();
//    }
//
//    /**
//     * 清理文件的排程 Trigger
//     */
//    @Bean
//    public CronTriggerFactoryBean cronTriggerFactoryBean(Job fileCleanupJob) {
//        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
//        jobDetailFactoryBean.setTargetObject(fileCleanupJob);
//        jobDetailFactoryBean.setTargetMethod("execute");
//        jobDetailFactoryBean.setConcurrent(false);
//
//        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
//        cronTriggerFactoryBean.setJobDetail(Objects.requireNonNull(jobDetailFactoryBean.getObject()));
//        cronTriggerFactoryBean.setCronExpression(cleanUpProperties.getCronExpression());
//        return cronTriggerFactoryBean;
//
//    }
//
//    private boolean isExpired(File file, Duration expirationDays) {
//        String fileName = file.getName();
//        String[] parts = fileName.split("_");
//
//        if (parts.length >= 2) {
//            String timestampString = parts[1];
//            LocalDateTime fileTimestamp = LocalDateTime.parse(timestampString, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//            LocalDateTime expirationDate = LocalDateTime.now().minus(expirationDays);
//            return fileTimestamp.isBefore(expirationDate);
//        }
//        return false;
//    }
//
//}
//
//
