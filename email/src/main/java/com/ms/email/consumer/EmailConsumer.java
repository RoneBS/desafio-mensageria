package com.ms.email.consumer;

import com.ms.email.api.dto.EmailRecordDto;
import com.ms.email.domain.entities.Email;
import com.ms.email.domain.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenerEmailQueue(@Payload EmailRecordDto emailRecordDto) {
        var email = new Email();
        BeanUtils.copyProperties(emailRecordDto, email);
        emailService.sendEmail(email);
    }
}
