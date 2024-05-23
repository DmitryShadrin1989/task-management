package ru.task.management.controller;

import ru.task.management.dto.TaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.task.management.service.TaskService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskRestController {

    private final TaskService taskService;

    @GetMapping("/api/task-management/task")
    public ResponseEntity<List<TaskDto>> getListTasks(@RequestParam(name = "boardId") String boardId) {
        if (boardId != null) {
            return ResponseEntity.ok(TaskDto.toDtoList(taskService.getListOfTasksForBoard(boardId)));
        }
        return ResponseEntity.ok(TaskDto.toDtoList(taskService.findAll()));
    }

    @GetMapping("/api/task-management/task/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable String id) {
        return ResponseEntity.ok(new TaskDto(taskService.findById(id)));
    }

    @PutMapping("/api/task-management/task/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable String id, @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(new TaskDto(taskService.update(taskDto)));
    }

    @PostMapping("/api/task-management/task")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(new TaskDto(taskService.insert(taskDto)));
    }
}
