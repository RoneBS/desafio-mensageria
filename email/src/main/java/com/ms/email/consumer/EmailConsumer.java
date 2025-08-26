package com.ms.email.consumer;

import com.ms.email.api.dto.EmailRecordDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenerEmailQueue(@Payload EmailRecordDto emailRecordDto) {
        System.out.println(emailRecordDto.emailTo());
    }
}
//01:07:15