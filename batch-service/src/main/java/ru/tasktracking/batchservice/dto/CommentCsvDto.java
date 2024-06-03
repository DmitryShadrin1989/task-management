package ru.tasktracking.batchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentCsvDto {

    private Long id;

    private Long authorId;

    private Long taskId;

    private LocalDateTime creationDate;

    private String comment;
}
