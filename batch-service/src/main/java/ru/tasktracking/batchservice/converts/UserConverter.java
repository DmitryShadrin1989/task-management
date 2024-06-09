package ru.tasktracking.batchservice.converts;

import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.tasktracking.batchservice.domain.User;
import ru.tasktracking.batchservice.dto.UserCsvDto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserConverter {

    private final Map<Long, User> userIdsMap;

    private final PasswordEncoder passwordEncoder;

    public UserConverter(PasswordEncoder passwordEncoder) {
        this.userIdsMap = new ConcurrentHashMap<>();
        this.passwordEncoder = passwordEncoder;
    }

    public String userToString(User user) {
        return "Id: %s, Username: %s".formatted(user.getId(), user.getUsername());
    }

    public User getUser(Long userCsvId) {
        return userIdsMap.get(userCsvId);
    }

    public User convertToDomain(UserCsvDto userCsvDto) {
        String userMongoId = new ObjectId().toString();
        User user = new User(userMongoId, userCsvDto.getUsername(), passwordEncoder.encode(userCsvDto.getPassword()));
        userIdsMap.put(userCsvDto.getId(), user);
        return user;
    }
}
