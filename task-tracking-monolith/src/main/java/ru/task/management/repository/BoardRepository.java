package ru.task.management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.task.management.domain.Board;
import ru.task.management.domain.User;

import java.util.List;

public interface BoardRepository extends MongoRepository<Board, String> {
    List<Board> findAllByExecutorsContains(User executor);
}
