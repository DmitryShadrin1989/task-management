package ru.task.management.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class InvalidJwtTokenException extends BadCredentialsException {

    private static final String MESSAGE = "This JWT token is not valid";

    public InvalidJwtTokenException() {
        super(MESSAGE);
    }

    public InvalidJwtTokenException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
