package ru.task.management.service;

import ru.task.management.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
}
