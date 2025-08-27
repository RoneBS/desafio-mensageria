package com.ms.email.consumer;

import com.ms.email.api.dto.EmailRecordDto;
import com.ms.email.domain.entities.Email;
import com.ms.email.domain.service.EmailService;
import com.ms.email.exception.EmailSendingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class EmailConsumer {

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenerEmailQueue(@Payload EmailRecordDto emailRecordDto) {
        try {
            var email = new Email();
            BeanUtils.copyProperties(emailRecordDto, email);
            emailService.sendEmail(email);
        } catch (Exception e) {
            // Se o envio do e-mail falhar, lança uma exceção personalizada.
            // Isso garante que o RabbitMQ saiba que a mensagem não foi processada com sucesso.
            throw new EmailSendingException("Falha ao enviar ensagem de email.", e);
        }
    }
}
