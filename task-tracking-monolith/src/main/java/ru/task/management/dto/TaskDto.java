package ru.task.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.task.management.domain.Task;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private String id;

    private String name;

    private String describe;

    private String boardId;

    private UserDto author;

    private UserDto executor;

    private UserDto reviewer;

    private LocalDate creationDate;

    private LocalDate plannedCompletionDate;

    private LocalDate actualCompletionDate;

    private String taskStatusValue;

    public TaskDto(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.describe = task.getDescribe();
        this.boardId = task.getBoard().getId();
        this.author = new UserDto(task.getAuthor());
        this.executor = new UserDto(task.getExecutor());
        this.reviewer = new UserDto(task.getReviewer());
        this.creationDate = task.getCreationDate();
        this.plannedCompletionDate = task.getPlannedCompletionDate();
        this.actualCompletionDate = task.getActualCompletionDate();
        this.taskStatusValue = task.getTaskStatus().getValue();
    }

    public static List<TaskDto> toDtoList(List<Task> tasks) {
        return tasks.stream()
                .map(TaskDto::new)
                .toList();
    }
}
