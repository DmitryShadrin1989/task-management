package ru.tasktracking.batchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskCsvDto {

    private Long id;

    private String name;

    private String describe;

    private Long boardId;

    private Long authorId;

    private Long executorId;

    private Long reviewerId;

    private LocalDate creationDate;

    private LocalDate plannedCompletionDate;

    private LocalDate actualCompletionDate;

    private String taskStatus;
}
