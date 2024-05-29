package ru.tasktracking.batchservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.tasktracking.batchservice.domain.Board;
import ru.tasktracking.batchservice.domain.User;

import java.util.List;

public interface BoardRepository extends MongoRepository<Board, String> {
    List<Board> findAllByExecutorsContains(User executor);
}
