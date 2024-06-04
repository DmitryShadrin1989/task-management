package ru.tasktracking.batchservice.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.tasktracking.batchservice.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
