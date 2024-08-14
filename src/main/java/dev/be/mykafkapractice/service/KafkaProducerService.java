package dev.be.mykafkapractice.service;

import dev.be.mykafkapractice.dto.MessageDto;
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
    private final KafkaTemplate<String, MessageDto> newKafkaTemplate;
    private final static String TOPIC_NAME = "springTopic";

    public void send(String message) {
        kafkaTemplate.send(TOPIC_NAME, message);
    }

    public void sendWithCallBack(String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC_NAME, message);

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

    //https://stackoverflow.com/questions/77951258/replacing-listenablefuture-with-completablefuture-in-kafka-producer-consumer
    public void sendWithCallbackVer2(String message){
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC_NAME, message);

        future
                .thenAcceptAsync(result -> log.info("message : {} offset : {}", message, result.getRecordMetadata().offset()))
                .exceptionallyAsync(throwable -> {
                    log.warn("failed : {}  due to : {}", message, throwable.getMessage());
                    return null;
                    //this usually lets you handle exceptions - like providing a value in case or errors - so you can continue the chain, but in this case, nothing doing so...
                });
    }

    private void sendWithCallbackDeprecated(String message) {
//        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC_NAME, message);
//
//        future.addCallback(new ListenableFutureCallback<>() {
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
    }

    public void sendJson(MessageDto message){
        newKafkaTemplate.send(TOPIC_NAME,message);
    }
}
