package ru.task.management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.task.management.domain.Task;

import java.util.List;
import java.util.Map;

public interface TaskRepository extends MongoRepository<Task, String> {

    List<Task> findAllByBoardId(String boardId);
}
