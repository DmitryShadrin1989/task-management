package ru.tasktracking.gatewayservice.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.tasktracking.gatewayservice.domain.User;
import ru.tasktracking.gatewayservice.exeption.IncorrectPasswordException;
import ru.tasktracking.gatewayservice.exeption.UserNotFoundException;
import ru.tasktracking.gatewayservice.service.UserService;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (username != null) {
            User user = userService.findUserByUsernameIgnoreCase(username);

            if (user == null) {
                throw new UserNotFoundException();
            }
            try {
                if (passwordEncoder.matches(password, user.getPassword())) {
                    return new UsernamePasswordAuthenticationToken(user, null, List.of());
                } else {
                    throw new IncorrectPasswordException();
                }
            } catch (Exception ex) {
                throw new IncorrectPasswordException(ex);
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
