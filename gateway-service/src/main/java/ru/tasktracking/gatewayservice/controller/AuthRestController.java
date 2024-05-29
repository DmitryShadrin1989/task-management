package ru.tasktracking.gatewayservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tasktracking.gatewayservice.dto.LoginRequestDto;
import ru.tasktracking.gatewayservice.dto.LoginResponseDto;
import ru.tasktracking.gatewayservice.dto.UserDto;
import ru.tasktracking.gatewayservice.service.AuthService;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<LoginResponseDto> authUser(
            @RequestBody LoginRequestDto request) {
        String token = authService.auth(request);
        UserDto user = authService.getCurrentUser();
        return ResponseEntity.ok(new LoginResponseDto(user, token));
    }

    @GetMapping("/api/auth/current")
    public ResponseEntity<UserDto> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
