package ru.task.management.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.task.management.dto.LoginRequestDto;
import ru.task.management.dto.UserDto;
import ru.task.management.service.AuthService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<String> authUser(
            @RequestBody LoginRequestDto request) {
        String token = authService.auth(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/api/auth/current")
    public ResponseEntity<UserDto> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
