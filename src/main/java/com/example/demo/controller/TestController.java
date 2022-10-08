package com.example.demo.controller;

import com.example.demo.kafka.producer.KafkaProducerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private KafkaProducerClient kafkaProducerClient;

    @GetMapping("/produce")
    public String produce(){
        //for (int i = 1; i < 11; i++){
            kafkaProducerClient.produceClearCacheEvent("test","Hello-");
        //}
        //kafkaProducerClient.produceClearCacheEvent("test","Hello");
        return "Done";
    }
}
