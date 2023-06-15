package com.sqy.rabbitmq;

import com.sqy.domain.email.Email;
import com.sqy.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMqConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "emailQueue")
    public void receiveMessage(Email email) {
        emailService.sendEmail(email);
    }
}
