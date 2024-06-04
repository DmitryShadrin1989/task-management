package ru.tasktracking.batchservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.tasktracking.batchservice.domain.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

}
