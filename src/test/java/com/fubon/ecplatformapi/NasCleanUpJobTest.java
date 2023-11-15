//package com.fubon.ecplatformapi;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.File;
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@EnableBatchProcessing
//@EnableAutoConfiguration
//public class NasCleanUpJobTest {
//
//    @Autowired
//    private JobLauncher jobLauncher;
//    @Autowired
//    private Job fileCleanupJob;
//    @Autowired
//    private NasCleanUpJob nasCleanUpJob;
//
//    @Test
//    public void testJobExecution() throws Exception {
//
//        JobExecution jobExecution = jobLauncher.run(fileCleanupJob, new JobParameters());
//
//        log.info("檢查 Job 的執行狀態");
//        assert jobExecution.getStatus().isRunning();
//
//        TimeUnit.SECONDS.sleep(10);
//
//        log.info("檢查 NAS 資料夾是否有被清理");
//        String path = "your-nas-folder-path";
//        File nasDirectory = new File(path);
//        File[] files = nasDirectory.listFiles();
//
//    }
//}
