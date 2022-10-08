package com.example.demo.thread;

import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Component
public class ProcessTask {



    @Autowired
    private TestService testService;

    public Runnable newRunnable(String message) {
        return () -> {
            System.out.println("--------Processing message : " + message + " by Current Processing Thread - " + Thread.currentThread().getName());
            //testService.test();
        };
    }
}
