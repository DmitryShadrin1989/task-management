package ru.tasktracking.taskservice.service;

import ru.tasktracking.taskservice.domain.Task;
import ru.tasktracking.taskservice.dto.TaskDto;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

    List<Task> getListOfTasksForBoard(String boardId);

    Task findById(String id);

    Task insert(TaskDto taskDto);

    Task update(TaskDto taskDto);
}
