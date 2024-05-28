package ru.task.management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.task.management.domain.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findUserByUsernameIgnoreCase(String login);
}
