package ru.tasktracking.gatewayservice.service;

import ru.tasktracking.gatewayservice.dto.LoginRequestDto;
import ru.tasktracking.gatewayservice.dto.UserDto;

public interface AuthService {

    String auth(LoginRequestDto request);

    UserDto getCurrentUser();
}