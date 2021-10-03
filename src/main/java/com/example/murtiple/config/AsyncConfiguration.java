package com.example.murtiple.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@Slf4j
public class AsyncConfiguration {

    /*
    如果不定义这个 executor, 那么就会使用以下的 configuration 类
    org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration

	@Lazy
	@Bean(name = { APPLICATION_TASK_EXECUTOR_BEAN_NAME,
			AsyncAnnotationBeanPostProcessor.DEFAULT_TASK_EXECUTOR_BEAN_NAME })
	@ConditionalOnMissingBean(Executor.class)
	public ThreadPoolTaskExecutor applicationTaskExecutor(TaskExecutorBuilder builder) {
		return builder.build();
	}

     */
    @Bean(name = AsyncAnnotationBeanPostProcessor.DEFAULT_TASK_EXECUTOR_BEAN_NAME) // 使用这个名字感觉更加标准
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        /*
          Core pool size is defines minimum paralel threads can run at same time.
          In our sample, CORE_POOL_SIZE = 75 and this mean,
          our application can increase paralel running threads up to 75.
          If our application need more thread over than 75,
          new threads will be added into queue.
          也就是说，平常最多的并行线程数,

          实际上，无论 MaxPoolSize 和 QueueCapacity 是多少，都是按照这个线程数运行
         */
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());

        /*
         * Maximum pool size defines maximum parallel threads can run at same time.
         * In our sample MAX_POOL_SIZE = 100 and this mean,
         * our application can be increase to 100 parallel running threads when queue is full.
         * 也就是说，最大容纳数字
         */
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());

        /*
         * Queue is using when all core pool are filled.
         * Threads will be scalable to maximum pool size when queue is full.
         * In our application, let’s imagine 75 threads running at same time and also 75 threads more in queue.
         * Totally we have 150 threads.
         * Pool size will be increased until maximum pool size for each thread over 150.
         * 如果超出 core size, 那么可以放在 Queue 的，等待放入 max pool 的数字。
         * 其实也就是说，只要 Queue 够大，那个 pool 多少没有关系
         */
        executor.setQueueCapacity(1024);
        executor.setThreadNamePrefix("Vincent task - ");
        executor.setWaitForTasksToCompleteOnShutdown(false);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
