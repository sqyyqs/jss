package com.sqy.rabbitmq;

import com.sqy.domain.email.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMqProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String queueName, Email email) {
        rabbitTemplate.convertAndSend(queueName, email);
    }
}
