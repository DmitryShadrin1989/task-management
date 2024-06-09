package ru.tasktracking.commentservice.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.tasktracking.commentservice.domain.User;

import java.util.Optional;


@FeignClient(name = "user-service")
public interface UserService {

    @GetMapping(value = "/api/user/{id}")
    Optional<User> findById(@PathVariable String id);
}
