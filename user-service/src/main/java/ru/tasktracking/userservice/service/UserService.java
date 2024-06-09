package ru.tasktracking.userservice.service;

import ru.tasktracking.userservice.domain.User;
import ru.tasktracking.userservice.dto.LoginRequestDto;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(String id);

    User update(String id, LoginRequestDto loginRequestDto);

    User insert(LoginRequestDto loginRequestDto);

    User findByUserName(String username);
}
