package com.example.murtiple.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AsyncService {

    /*
    如果被 @Async 注解修饰的方法，返回值只能是 void 或者 Future 的继承类
     */
    // 这里使用这个 bean 名是由 AsyncConfiguration 决定的
    @Async(AsyncAnnotationBeanPostProcessor.DEFAULT_TASK_EXECUTOR_BEAN_NAME)
    public CompletableFuture<String> asyncMethod() {
        try {
            TimeUnit.SECONDS.sleep(1);
            log.info(Thread.currentThread().getName() + ": See if I am running in async");
        } catch (Exception e) {
            log.error(Thread.currentThread().getName() + ":" + e.getMessage());
        }
        return CompletableFuture.completedFuture("finish");
    }

}
