package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.properties.CleanUpProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class NasCleanUpJob {
    @Autowired
    private CleanUpProperties cleanUpProperties;

    /**
     * 影像清理
     *
     */
    @Scheduled(cron = "${scheduling.cron-expression}")
    public void fileCleanupTasklet() {
        System.out.println("現在時間：" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + " - Nas影像清理 #Start!");
        log.info("現在時間：" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + " - Nas影像清理 #Start!");

        System.out.println("Nas排程時間: " + cleanUpProperties.getCronExpression());
        log.info("Nas排程時間: " + cleanUpProperties.getCronExpression());

        String filePath = cleanUpProperties.getCleanPath();
        Duration expirationDays = cleanUpProperties.getExpirationDays();

        log.info("Nas清理路徑: " + filePath + " ,Nas檔案過期時間: " + expirationDays.toDays());

        File nasDirectory = new File(filePath);
        File[] files = nasDirectory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (isExpired(file, expirationDays)) {
                    if (file.delete()) {
                        log.info("刪除過期的檔案: " + file.getName() + "Path: " + file.getAbsolutePath());
                    } else {
                        log.info("沒有過期的檔案: " + file.getName() + "Path: " + file.getAbsolutePath());
                    }
                }
            }
        }
    }

    private boolean isExpired(File file, Duration expirationDays) {
        String fileName = file.getName();
        String[] parts = fileName.split("_");

        if (parts.length >= 2) {
            String timestampString = parts[1];
            try {
                LocalDateTime fileTimestamp = LocalDateTime.parse(timestampString, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                LocalDateTime expirationDate = LocalDateTime.now().minus(expirationDays);
                return fileTimestamp.isBefore(expirationDate);
            }catch (DateTimeException e){
                System.out.println("日期解析錯誤，文件名：" + fileName + " ,ErrMsg: " + e.getMessage());
                log.info("日期解析錯誤，文件名：" + fileName + " ,ErrMsg: " + e.getMessage());
                return false;
            }
        }
        return false;
    }
}


