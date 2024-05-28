package ru.tasktracking.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.tasktracking.userservice.domain.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findUserByUsernameIgnoreCase(String login);
}
