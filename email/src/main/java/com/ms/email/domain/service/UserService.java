package com.ms.email.domain.service;

import com.ms.email.domain.entities.Email;
import com.ms.email.domain.enums.StatusEmail;
import com.ms.email.domain.repository.EmailRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class UserService {

    private final EmailRepository emailRepository;
    private final JavaMailSender emailSender;

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    public Email sendEmail(Email email) {
        try {
            email.setSendDateEmail(LocalDateTime.now());
            email.setEmailFrom(emailFrom);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());
            emailSender.send(message);

            email.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            email.setStatusEmail(StatusEmail.ERROR);
        }finally {
            return emailRepository.save(email);
        }
    }
}
