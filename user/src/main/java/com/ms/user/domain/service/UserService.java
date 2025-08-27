package com.ms.user.domain.service;

import com.ms.user.domain.entities.User;
import com.ms.user.domain.repository.UserRepository;
import com.ms.user.exception.MessagePublicationException;
import com.ms.user.producer.UserProducer;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProducer userProducer;

    @Transactional
    public User save(User user) {
        User savedUser = userRepository.save(user);

        try {
            // Publica a mensagem para a fila
            userProducer.publishMessageEmail(savedUser);
        } catch (Exception e) {
            // Se a publicação da mensagem falhar, lança uma exceção de runtime.
            // Isso fará com que a transação @Transactional faça rollback,
            // desfazendo a criação do usuário no banco de dados.
            throw new MessagePublicationException("Falha ao publicar mensagem para o email.", e);
        }

        return savedUser;
    }
}
