package ru.tasktracking.taskservice.repository;

import ru.tasktracking.taskservice.domain.Task;
import ru.tasktracking.taskservice.filter.TaskBoardFilter;

import java.util.List;

public interface TaskRepositoryCustom {

    List<Task> findByBoardFilter(TaskBoardFilter filter);
}
