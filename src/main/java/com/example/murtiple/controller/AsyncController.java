package com.example.murtiple.controller;

import com.example.murtiple.async.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.Executor;

@RestController
@Slf4j
public class AsyncController implements ApplicationContextAware {

    ApplicationContext applicationContext;

    @Resource
    private AsyncService asyncService;

    @GetMapping("async1")
    public String tryAsyncMethod(@RequestParam int num) {
        asyncService.asyncMethod();
        return "hello world";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
