package ru.task.management.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.task.management.domain.User;
import ru.task.management.exception.IncorrectPasswordException;
import ru.task.management.exception.UserNotFoundException;
import ru.task.management.repository.UserRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (username != null) {
            User user = userRepository.findUserByUsernameIgnoreCase(username);

            if (user != null) {
                try {
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        return new UsernamePasswordAuthenticationToken(user, null, List.of());
                    } else {
                        throw new IncorrectPasswordException();
                    }
                } catch (Exception ex) {
                    throw new IncorrectPasswordException(ex);
                }
            } else {
                throw new UserNotFoundException();
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
