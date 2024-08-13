package dev.be.mykafkapractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final static String TOPIC_NAME = "springTopic";

    public void send(String message) {
        kafkaTemplate.send(TOPIC_NAME, message);
    }
}
