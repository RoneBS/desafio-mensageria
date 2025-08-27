package com.ms.user.producer;

import com.ms.user.api.dto.EmailDto;
import com.ms.user.domain.entities.User;
import com.ms.user.exception.MessagePublicationException;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class UserProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishMessageEmail(User user) {
        var emailDto = new EmailDto();
        emailDto.setUserId(user.getUserId());
        emailDto.setEmailTo(user.getEmail());
        emailDto.setSubject("Cadastro realizado com sucesso!");
        emailDto.setText(user.getName() + ", seja bem vindo(a)! \nAgora você pode aproveitar todos os beneficios da nossa plataforma.");

        try {
            // Tenta enviar a mensagem para a fila
            rabbitTemplate.convertAndSend("", routingKey, emailDto);
        } catch (AmqpException e) {
            // Se houver qualquer erro na conexão ou na publicação,
            // lança uma exceção personalizada.
            throw new MessagePublicationException("Falha ao publicar mensagem para o email.", e);
        }
    }
}

