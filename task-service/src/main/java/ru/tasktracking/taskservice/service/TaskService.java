package ru.tasktracking.taskservice.service;

import ru.tasktracking.taskservice.domain.Task;
import ru.tasktracking.taskservice.dto.TaskDto;
import ru.tasktracking.taskservice.filter.TaskBoardFilter;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

    List<Task> getListOfTasksForBoard(String boardId);

    List<Task> getFilteredTasksForBoard(TaskBoardFilter filter);

    Task findById(String id);

    Task insert(TaskDto taskDto);

    Task update(TaskDto taskDto);
}
