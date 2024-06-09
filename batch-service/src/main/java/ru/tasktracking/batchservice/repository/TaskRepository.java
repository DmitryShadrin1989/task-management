package ru.tasktracking.batchservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.tasktracking.batchservice.domain.Task;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {

}
