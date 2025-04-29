package com.uhuru.userservice.utility;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Getter
public class ApplicationProp {

    @Value("${spring.kafka.producer.properties.schema.registry.url}")
    private String schemaRegistryUrl;


    @Value("${app.kafka.topics.user-created-events:user-created-events}")
    private String topicName;

}
