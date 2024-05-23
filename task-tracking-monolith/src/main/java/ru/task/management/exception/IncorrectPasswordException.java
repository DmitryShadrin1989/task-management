package ru.task.management.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class IncorrectPasswordException extends BadCredentialsException {

    private static final String MESSAGE = "Invalid password for this login";

    public IncorrectPasswordException() {
        super(MESSAGE);
    }

    public IncorrectPasswordException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
