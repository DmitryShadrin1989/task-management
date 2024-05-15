package ru.task.management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.task.management.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
}
