package com.ms.user.producer;

import com.ms.user.api.dto.EmailDto;
import com.ms.user.domain.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserProducer {

    final RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.queue.email.name}") //mesma chave utilizada no email
    private String routingKey;

    public void publishMessageEmail(User user) {
        var emailDto = new EmailDto();
        emailDto.setUserId(user.getUserId());
        emailDto.setEmailTo(user.getEmail());
        emailDto.setSubject("Cadastro realizado com sucesso!");
        emailDto.setText(user.getName() + ", seja bem vindo(a)! \nAgora você pode aproveitar todos os beneficios da nossa plataforma.");

        rabbitTemplate.convertAndSend("", routingKey, emailDto);

    }
}
