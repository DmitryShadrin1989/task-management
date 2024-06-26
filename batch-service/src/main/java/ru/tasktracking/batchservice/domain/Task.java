package ru.tasktracking.batchservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;

    private String name;

    private String describe;

    @DBRef
    private Board board;

    @DBRef
    private User author;

    @DBRef
    private User executor;

    @DBRef
    private User reviewer;

    private LocalDate creationDate;

    private LocalDate plannedCompletionDate;

    private LocalDate actualCompletionDate;

    private TaskStatus taskStatus;
}
