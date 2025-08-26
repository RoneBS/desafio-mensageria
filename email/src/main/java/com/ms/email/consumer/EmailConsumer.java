package com.ms.email.consumer;

import com.ms.email.api.dto.EmailRecordDto;
import com.ms.email.domain.entities.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenerEmailQueue(@Payload EmailRecordDto emailRecordDto) {
        var email = new Email();
        BeanUtils.copyProperties(emailRecordDto, email);
    }
}
