package com.example.demo.service.impl;

import com.example.demo.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public void test(String message, String topic) {

        int n = Integer.parseInt(message);
        if ("test".equals(topic)) {
            if (n % 2 == 0) {
                System.out.println("Exception1 occurred for n -> " + n);
                throw new RuntimeException("test");
            }
        } else if ("test-retry-0".equals(topic)) {
            if (n % 4 == 0) {
                System.out.println("Exception2 occurred for n -> " + n);
                throw new RuntimeException("test-retry-0");
            }
        } else if ("test-retry-1".equals(topic)) {
            if (n >= 10) {
                System.out.println("Exception3 occurred for n -> " + n);
                throw new RuntimeException("test-retry-1");
            }
        }
    }
}
