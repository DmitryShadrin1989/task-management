package ru.task.management.service;

import dto.TaskDto;
import ru.task.management.domain.Task;

import java.util.List;
import java.util.Map;

public interface TaskService {

    List<Task> findAll();

    List<Task> getListOfTasksForBoard(String boardId);

    Task findById(String id);

    Task insert(TaskDto taskDto);

    Task update(TaskDto taskDto);
}
