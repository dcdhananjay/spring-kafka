package com.example.demo.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducerClient {
    @Autowired
    @Qualifier("cacheEventKafkaTemplate")
    private KafkaTemplate<String,String> cacheEventKafkaTemplate;

    public void produceClearCacheEvent(String topic, String payload) {
        try {
            ListenableFuture<SendResult<String, String>> future = cacheEventKafkaTemplate.send(topic, payload);
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("Error occurred while adding data" + ex);
                }

                @Override
                public void onSuccess(SendResult<String, String> result) {
                    System.out.println("Message sent successfully " + result);
                }
            });
        }
        catch (Exception exp){
            System.out.println("Exception occured in produceClearCacheEvent" + exp);
        }

    }
}
