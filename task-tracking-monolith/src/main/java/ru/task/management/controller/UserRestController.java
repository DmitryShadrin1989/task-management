package ru.task.management.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.task.management.dto.LoginRequestDto;
import ru.task.management.dto.TaskDto;
import ru.task.management.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/api/task-management/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {
        return ResponseEntity.ok(new UserDto(userService.findById(id)));
    }

    @PostMapping("/api/task-management/user")
    public ResponseEntity<UserDto> createUser(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(new UserDto(userService.insert(loginRequestDto)));
    }
}
