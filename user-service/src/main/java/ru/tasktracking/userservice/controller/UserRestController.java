package ru.tasktracking.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tasktracking.userservice.domain.User;
import ru.tasktracking.userservice.dto.LoginRequestDto;
import ru.tasktracking.userservice.dto.UserDto;
import ru.tasktracking.userservice.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/api/user")
    public ResponseEntity<List<UserDto>> getListUsers() {
        log.info("calling the method: getListUsers");
        return ResponseEntity.ok(UserDto.toDtoList(userService.findAll()));
    }

    @GetMapping("/api/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        log.info("calling the method: getUserById");
        return ResponseEntity.ok(new UserDto(userService.findById(id)));
    }

    @GetMapping("/api/user/username/{username}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String username) {
        log.info("calling the method: getUserByUserName");
        return ResponseEntity.ok(userService.findByUserName(username));
    }

    @PostMapping("/api/user")
    public ResponseEntity<UserDto> createUser(@RequestBody LoginRequestDto loginRequestDto) {
        log.info("calling the method: createUser");
        return ResponseEntity.ok(new UserDto(userService.insert(loginRequestDto)));
    }
}
