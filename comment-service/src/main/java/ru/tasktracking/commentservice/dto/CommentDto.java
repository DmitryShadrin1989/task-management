package ru.tasktracking.commentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tasktracking.commentservice.domain.Comment;
import ru.tasktracking.commentservice.domain.Task;
import ru.tasktracking.commentservice.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private String id;

    private User author;

    private Task task;

    private LocalDateTime creationDate;

    private String comment;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.author = comment.getAuthor();
        this.task = comment.getTask();
        this.creationDate = comment.getCreationDate();
        this.comment = comment.getComment();
    }

    public static List<CommentDto> toDtoList(List<Comment> comments) {
        return comments.stream()
                .map(CommentDto::new)
                .toList();
    }
}
