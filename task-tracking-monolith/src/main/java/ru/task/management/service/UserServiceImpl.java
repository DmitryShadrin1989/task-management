package ru.task.management.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.management.domain.User;
import ru.task.management.dto.LoginRequestDto;
import ru.task.management.exception.EntityNotFoundException;
import ru.task.management.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id %s not found".formatted(id)));
    }

    @Override
    @Transactional
    public User update(String id, LoginRequestDto loginRequestDto) {
        return save(id, loginRequestDto.getUsername(), passwordEncoder.encode(loginRequestDto.getPassword()));
    }

    @Override
    @Transactional
    public User insert(LoginRequestDto loginRequestDto) {
        return save(null, loginRequestDto.getUsername(), passwordEncoder.encode(loginRequestDto.getPassword()));
    }

    private User save(String id, String username, String password) {
        return userRepository.save(new User(id, username, password));
    }
}
