package com.hlxd.microcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/11/3014:51
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
@EnableAsync
@Configuration
public class AsyncConfig  {

    @Bean("AsyncConfigure")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        int cores = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(cores);
        // 设置最大线程数
        executor.setMaxPoolSize(20);
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置线程默认前缀名
        executor.setThreadNamePrefix("AsyncConfigure-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 注意，此时需要调用 initialize
        executor.initialize();
        return executor;
    }
}
