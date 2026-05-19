package ru.tasktracking.taskservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.tasktracking.taskservice.domain.Task;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String>, TaskRepositoryCustom {

    List<Task> findAllByBoardId(String boardId);
}
