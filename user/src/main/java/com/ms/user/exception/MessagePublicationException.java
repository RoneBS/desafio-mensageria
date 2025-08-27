package com.ms.user.exception;

public class MessagePublicationException extends RuntimeException {
    public MessagePublicationException(String message, Throwable cause) {
        super(message, cause);
    }
}