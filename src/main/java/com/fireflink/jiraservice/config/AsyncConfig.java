package com.fireflink.jiraservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "emailExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Minimum threads
        executor.setMaxPoolSize(10); // Maximum threads
        executor.setQueueCapacity(100); // Queue size
        executor.setThreadNamePrefix("Email-Async-");
        executor.initialize();
        return executor;
    }
}
