package dev.be.mykafkapractice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final static String TOPIC_NAME = "springTopic";

    public void send(String message) {
        kafkaTemplate.send(TOPIC_NAME, message);
    }

    public void sendWithCallBack(String message) {
//        Deprecated
//        ListenableFuture<SendResult<String, String>> future1 = kafkaTemplate.send(TOPIC_NAME, message);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC_NAME, message);

//        future1.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                log.warn("failed : {}  due to : {}", message, ex.getMessage());
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, String> result) {
//                log.info("message : {}  offset : {}", message, result.getRecordMetadata().offset());
//            }
//        });

        future.whenComplete(
                (result, ex) -> {
                    if (ex == null) {
                        log.info("message : {} offset : {}", message, result.getRecordMetadata().offset());
                    } else {
                        log.warn("failed : {}  due to : {}", message, ex.getMessage());
                    }
                }
        );
    }
}
