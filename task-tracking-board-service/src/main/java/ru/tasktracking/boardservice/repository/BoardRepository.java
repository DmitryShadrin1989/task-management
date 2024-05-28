package ru.tasktracking.boardservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.tasktracking.boardservice.domain.Board;
import ru.tasktracking.boardservice.domain.User;

import java.util.List;

public interface BoardRepository extends MongoRepository<Board, String> {
    List<Board> findAllByExecutorsContains(User executor);
}
