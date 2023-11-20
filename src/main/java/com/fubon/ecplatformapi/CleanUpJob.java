//package com.fubon.ecplatformapi;
//
//import com.fubon.ecplatformapi.properties.CleanUpProperties;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.text.SimpleDateFormat;
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//import java.util.stream.Stream;
//
//@Slf4j
//@Configuration
//@EnableScheduling
//public class CleanUpJob {
//    @Autowired
//    CleanUpProperties cleanUpProperties;
//    public void sayHello() {
//        System.out.println("Hello:");
//    }
//
//    /**
//     * 影像清理
//     *
//     */
//    public RepeatStatus fileCleanupTasklet() {
//        String path = null;
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//        log.info("Nas Clean Up Job #Start: " + LocalTime.now().format(formatter));
//
//        String filePath = Paths.get(path, "EcPlatForm", path).toString();
//
//        Duration expirationDays = cleanUpProperties.getExpirationDays();
//        log.info("Expire days: " + expirationDays.toDays());
//        File nasDirectory = new File(filePath);
//        File[] files = nasDirectory.listFiles();
//
//        if (files != null) {
//            for (File file : files) {
//                if (isExpired(file, expirationDays)) {
//                    if (file.delete()) {
//                        log.info("Deleted expired file: " + file.getName());
//                        log.info("Path: " + file.getAbsolutePath());
//                    } else {
//                        log.error("Failed to delete file: " + file.getName());
//                        log.info("Path: " + file.getAbsolutePath());
//                    }
//                }
//            }
//        }
//        log.info("Nas Clean Up Job #End: " + LocalTime.now().format(formatter));
//        return RepeatStatus.FINISHED;
//    }
//
//        private boolean isExpired(File file, Duration expirationDays) {
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
//}
