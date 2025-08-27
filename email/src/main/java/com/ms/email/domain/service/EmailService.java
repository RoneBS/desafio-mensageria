package com.ms.email.domain.service;

import com.ms.email.domain.entities.Email;
import com.ms.email.domain.enums.StatusEmail;
import com.ms.email.domain.repository.EmailRepository;
import com.ms.email.exception.EmailSendingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender emailSender;

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    public EmailService(EmailRepository emailRepository, JavaMailSender emailSender) {
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
    }

    @Transactional
    public Email sendEmail(Email email) {
        email.setSendDateEmail(LocalDateTime.now());
        email.setEmailFrom(emailFrom);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());
            emailSender.send(message);

            email.setStatusEmail(StatusEmail.SENT);
            return emailRepository.save(email);

        } catch (MailException e) {
            email.setStatusEmail(StatusEmail.ERROR);
            emailRepository.save(email); // Salva o e-mail com status de erro

            // Lança uma exceção de runtime para sinalizar a falha.
            // Isso permite que o chamador (como o EmailConsumer) saiba que o envio falhou
            // e possa decidir se deve fazer rollback ou lidar com a falha de outra forma.
            throw new EmailSendingException("Falha ao enviar email para " + email.getEmailTo(), e);
        }
    }
}
