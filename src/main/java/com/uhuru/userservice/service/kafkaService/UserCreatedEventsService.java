package com.uhuru.userservice.service.kafkaService;

import com.uhuru.userservice.data.kafkaRequest.UserCreatedEvent;
import com.uhuru.userservice.utility.LoggerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserCreatedEventsService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final LoggerService loggerService;

    public UserCreatedEventsService(KafkaTemplate<String, Object> kafkaTemplate, LoggerService loggerService) {
        this.kafkaTemplate = kafkaTemplate;
        this.loggerService = loggerService;
    }



    public void sendUserCreatedEvent(UserCreatedEvent event) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("user-created-events", event);

        future.thenAccept(result ->
                loggerService.log("Successfully sent UserCreatedEvent for user: {}", event.getUserNumber())
        ).exceptionally(ex -> {
            loggerService.log("Failed to send UserCreatedEvent");
            return null;
        });
    }
}
