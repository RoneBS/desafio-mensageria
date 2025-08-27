package com.ms.user.api.controller;

import com.ms.user.api.dto.UserRecordDto;
import com.ms.user.domain.entities.User;
import com.ms.user.domain.service.UserService;
import com.ms.user.exception.MessagePublicationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserRecordDto userRecordDto) {
        var user = new User();
        BeanUtils.copyProperties(userRecordDto, user);

        try {
            User savedUser = userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (MessagePublicationException e) {
            // Se a exceção de publicação de mensagem for capturada, retorna um erro 503
            // com uma mensagem amigável, indicando que o serviço de e-mail está indisponível.
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("O serviço de email não esta operante. tente novamente mais tarde.");
        }
    }
}
