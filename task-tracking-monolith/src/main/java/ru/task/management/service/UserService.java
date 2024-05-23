package ru.task.management.service;

import ru.task.management.domain.User;
import ru.task.management.dto.LoginRequestDto;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(String id);

    User update(String id, LoginRequestDto loginRequestDto);

    User insert(LoginRequestDto loginRequestDto);
}
