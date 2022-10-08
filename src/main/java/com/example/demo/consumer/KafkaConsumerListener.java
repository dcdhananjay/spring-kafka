package com.example.demo.consumer;

import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class KafkaConsumerListener {

    int count = 0;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 60000L, multiplier = 2.0),
            autoCreateTopics = "false",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
            kafkaTemplate = "cacheEventKafkaTemplate")
    @KafkaListener(topics = "test_mt", groupId = "test_gp", containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Payload String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Acknowledgment ack) {

        System.out.println(LocalDateTime.now() + " ->" + message + " from " + topic);
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

        ack.acknowledge();
    }

    @DltHandler
    public void dlt(String in, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Acknowledgment ack) {
        System.out.println(LocalDateTime.now() + " ->" + in + " from dd " + topic);
        ack.acknowledge();
    }
}
