package com.xdcplus.auto;

import com.xdcplus.tool.constants.NumberConstant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 * @author Rong.Jia
 * @date 2021/04/29 08:58
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "task.pool")
public class ExecutorConfig implements AsyncConfigurer {

    private int corePoolSize;

    private int maxPoolSize;

    private int keepAliveSeconds;

    private int queueCapacity;

    @Primary
    @Bean
    public TaskExecutor executor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        //核心线程池大小
        executor.setCorePoolSize(NumberConstant.ONE);
        //最大线程数
        executor.setMaxPoolSize(NumberConstant.FIFTY);
        //队列容量
        executor.setQueueCapacity(NumberConstant.ONE_HUNDRED_AND_ELEVEN);
        //活跃时间
        executor.setKeepAliveSeconds(NumberConstant.FIFTY);

        //线程名字前缀
        executor.setThreadNamePrefix("taskExecutor-");

        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        //核心线程池大小
        executor.setCorePoolSize(corePoolSize);
        //最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //队列容量
        executor.setQueueCapacity(queueCapacity);
        //活跃时间
        executor.setKeepAliveSeconds(keepAliveSeconds);

        //线程名字前缀
        executor.setThreadNamePrefix("asyncExecutor-");

        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> log.error("线程池执行任务发生未知异常 {}", ex.getMessage());
    }
}
