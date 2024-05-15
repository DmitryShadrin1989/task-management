package ru.task.management.service;

import dto.TaskDto;
import exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.management.domain.Task;
import ru.task.management.domain.TaskStatus;
import ru.task.management.repository.BoardRepository;
import ru.task.management.repository.TaskRepository;
import ru.task.management.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getListOfTasksForBoard(String boardId) {
        return taskRepository.findAllByBoardId(boardId);
    }

    @Override
    @Transactional(readOnly = true)
    public Task findById(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id %s not found".formatted(id)));
    }

    @Override
    @Transactional
    public Task update(TaskDto taskDto) {
        return save(taskDto.getId(), taskDto.getName(), taskDto.getDescribe(), taskDto.getBoardId(),
                taskDto.getAuthor().getId(), taskDto.getExecutor().getId(), taskDto.getReviewer().getId(),
                taskDto.getCreationDate(), taskDto.getPlannedCompletionDate(), taskDto.getActualCompletionDate(),
                taskDto.getTaskStatusValue());
    }

    @Override
    @Transactional
    public Task insert(TaskDto taskDto) {
        return save(null, taskDto.getName(), taskDto.getDescribe(), taskDto.getBoardId(),
                taskDto.getAuthor().getId(), taskDto.getExecutor().getId(), taskDto.getReviewer().getId(),
                taskDto.getCreationDate(), taskDto.getPlannedCompletionDate(), taskDto.getActualCompletionDate(),
                taskDto.getTaskStatusValue());
    }

    private Task save(String id, String name, String describe, String boardId, String authorId, String executorId,
                      String reviewerId, LocalDate creationDate, LocalDate plannedCompletionDate,
                      LocalDate actualCompletionDate, String taskStatusValue) {
        var board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board with id %s not found".formatted(boardId)));
        var author = userRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("User with id %s not found".formatted(authorId)));
        var executor = userRepository.findById(executorId)
                .orElseThrow(() -> new EntityNotFoundException("User with id %s not found".formatted(executorId)));
        var reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() -> new EntityNotFoundException("User with id %s not found".formatted(reviewerId)));
        var taskStatus = TaskStatus.findByValue(taskStatusValue);
        var task = new Task(id, name, describe, board, author, executor, reviewer, creationDate,
                plannedCompletionDate, actualCompletionDate, taskStatus);
        return taskRepository.save(task);
    }
}
