package ru.tasktracking.batchservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.tasktracking.batchservice.domain.Board;

public interface BoardRepository extends MongoRepository<Board, String> {
}
