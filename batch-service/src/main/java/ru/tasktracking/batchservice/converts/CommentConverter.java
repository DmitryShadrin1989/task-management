package ru.tasktracking.batchservice.converts;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import ru.tasktracking.batchservice.domain.Comment;
import ru.tasktracking.batchservice.dto.CommentCsvDto;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CommentConverter {

    private final Map<Long, Comment> commentIdsMap;

    private final UserConverter userConverter;

    private final TaskConverter taskConverter;

    public CommentConverter(UserConverter userConverter, TaskConverter taskConverter) {
        this.commentIdsMap = new ConcurrentHashMap<>();
        this.userConverter = userConverter;
        this.taskConverter = taskConverter;
    }

    public String commentToString(Comment comment) {
        return "Id: %s, Author: %s, Task: {Id: %s, Name: %s}, Creation date: %s Comment: %s".formatted(comment.getId(),
                userConverter.userToString(comment.getAuthor()), comment.getTask().getId(),
                comment.getTask().getName(), comment.getCreationDate(), comment.getComment());
    }

    public Comment getComment(Long commentCsvId) {
        return commentIdsMap.get(commentCsvId);
    }

    public Comment convertToDomain(CommentCsvDto commentCsvDto) {
        String commentMongoId = new ObjectId().toString();
        Comment comment = new Comment(commentMongoId, userConverter.getUser(commentCsvDto.getAuthorId()),
                taskConverter.getTask(commentCsvDto.getTaskId()), commentCsvDto.getCreationDate(),
                commentCsvDto.getComment());
        commentIdsMap.put(commentCsvDto.getId(), comment);
        return comment;
    }
}
