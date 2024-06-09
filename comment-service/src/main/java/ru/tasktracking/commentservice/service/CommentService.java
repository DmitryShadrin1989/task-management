package ru.tasktracking.commentservice.service;

import ru.tasktracking.commentservice.domain.Comment;
import ru.tasktracking.commentservice.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<Comment> findAll();

    List<Comment> getListOfCommentsForTaskId(String taskId);

    Comment findById(String id);

    Comment insert(CommentDto commentDto);

    Comment update(CommentDto commentDto);
}
