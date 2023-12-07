package com.fubon.ecplatformapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
public class NasCleanUpJobTest {

    @Autowired
    private NasCleanUpJob nasCleanUpJob;

    @Test
    public void testFileCleanupTasklet() {
        nasCleanUpJob.fileCleanupTasklet();
    }
}

