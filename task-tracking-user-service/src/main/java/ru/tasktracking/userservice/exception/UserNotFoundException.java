package ru.tasktracking.userservice.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE = "The user with this username is not registered in the system";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
