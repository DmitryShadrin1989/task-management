package ru.task.management.controller;

import dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.task.management.domain.User;
import ru.task.management.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/api/task-management/user")
    public ResponseEntity<List<UserDto>> getListUsers() {
        return ResponseEntity.ok(UserDto.toDtoList(userService.findAll()));
    }
}
