package dev.be.mykafkapractice;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.be.mykafkapractice.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    private final static String TOPIC_NAME = "springTopic";

    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = TOPIC_NAME)
    public void listenMessage(String jsonMessage){
        try {
            MessageDto message = objectMapper.readValue(jsonMessage,MessageDto.class);
            log.info("name : {} \\ message : {}",message.name(),message.message());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
