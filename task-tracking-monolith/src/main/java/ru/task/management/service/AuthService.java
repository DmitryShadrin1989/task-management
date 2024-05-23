package ru.task.management.service;

import ru.task.management.dto.LoginRequestDto;
import ru.task.management.dto.UserDto;

public interface AuthService {

    String auth(LoginRequestDto request);

    UserDto getCurrentUser();
}
