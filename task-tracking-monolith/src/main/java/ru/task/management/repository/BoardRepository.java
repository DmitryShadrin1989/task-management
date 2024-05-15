package ru.task.management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.task.management.domain.Board;

public interface BoardRepository extends MongoRepository<Board, String> {
}
