package ru.tasktracking.gatewayservice.exeption;

import org.springframework.security.authentication.BadCredentialsException;

public class UserNotFoundException extends BadCredentialsException {

    private static final String MESSAGE = "The user with this username is not registered in the system";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
