package ru.tasktracking.batchservice.converts;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import ru.tasktracking.batchservice.domain.Task;
import ru.tasktracking.batchservice.domain.TaskStatus;
import ru.tasktracking.batchservice.dto.TaskCsvDto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TaskConverter {

    private final Map<Long, Task> taskIdsMap;

    private final UserConverter userConverter;

    private final BoardConverter boardConverter;

    public TaskConverter(UserConverter userConverter, BoardConverter boardConverter) {
        this.taskIdsMap = new ConcurrentHashMap<>();
        this.userConverter = userConverter;
        this.boardConverter = boardConverter;
    }

    public String taskToString(Task task) {
        return ("id: %s, name: %s, describe: %s, board: %s, author: %s, executor: %s, reviewer: %s, " +
                "creationDate: %s, plannedCompletionDate: %s, actualCompletionDate: %s, " +
                "taskStatus: %s,").formatted(task.getId(), task.getName(), task.getDescribe(),
                task.getBoard().getName(), task.getAuthor().getUsername(), task.getExecutor().getUsername(),
                task.getReviewer().getUsername(), task.getCreationDate().toString(),
                task.getPlannedCompletionDate().toString(), task.getActualCompletionDate().toString(),
                task.getTaskStatus().getValue());
    }

    public Task getTask(Long taskCsvId) {
        return taskIdsMap.get(taskCsvId);
    }

    public Task convertToDomain(TaskCsvDto taskCsvDto) {
        String taskMongoId = new ObjectId().toString();
        Task task = new Task(taskMongoId, taskCsvDto.getName(), taskCsvDto.getDescribe(),
                boardConverter.getBoard(taskCsvDto.getBoardId()), userConverter.getUser(taskCsvDto.getAuthorId()),
                userConverter.getUser(taskCsvDto.getExecutorId()), userConverter.getUser(taskCsvDto.getReviewerId()),
                taskCsvDto.getCreationDate(), taskCsvDto.getPlannedCompletionDate(),
                taskCsvDto.getActualCompletionDate(), TaskStatus.findByValue(taskCsvDto.getTaskStatus()));
        taskIdsMap.put(taskCsvDto.getId(), task);
        return task;
    }
}
