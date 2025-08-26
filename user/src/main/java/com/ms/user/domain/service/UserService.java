package com.ms.user.domain.service;

import com.ms.user.domain.entities.User;
import com.ms.user.domain.repository.UserRepository;
import com.ms.user.producer.UserProducer;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProducer userProducer;

    @Transactional
    public User save(User user) {
        user = userRepository.save(user);
        userProducer.publishMessageEmail(user);
        return user;
    }
}
