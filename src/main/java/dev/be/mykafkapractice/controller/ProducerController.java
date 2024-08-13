package dev.be.mykafkapractice.controller;

import dev.be.mykafkapractice.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final KafkaProducerService kafkaProducerService;
    @GetMapping("/pub")
    public String publish(@RequestParam String message){
        kafkaProducerService.send(message);
        return message;
    }
}
