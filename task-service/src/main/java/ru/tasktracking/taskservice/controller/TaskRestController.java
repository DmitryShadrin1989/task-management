package ru.tasktracking.taskservice.controller;

import com.mongodb.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tasktracking.taskservice.dto.TaskDto;
import ru.tasktracking.taskservice.service.TaskService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TaskRestController {

    private final TaskService taskService;

    @GetMapping("/api/task")
    public ResponseEntity<List<TaskDto>> getListTasks(@Nullable @RequestParam(name = "boardId") String boardId) {
        log.info("calling the method: getListTasks");
        if (boardId != null) {
            return ResponseEntity.ok(TaskDto.toDtoList(taskService.getListOfTasksForBoard(boardId)));
        }
        return ResponseEntity.ok(TaskDto.toDtoList(taskService.findAll()));
    }

    @GetMapping("/api/task/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable String id) {
        log.info("calling the method: getTask");
        return ResponseEntity.ok(new TaskDto(taskService.findById(id)));
    }

    @PutMapping("/api/task/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable String id, @RequestBody TaskDto taskDto) {
        log.info("calling the method: updateTask");
        return ResponseEntity.ok(new TaskDto(taskService.update(taskDto)));
    }

    @PostMapping("/api/task")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        log.info("calling the method: createTask");
        return ResponseEntity.ok(new TaskDto(taskService.insert(taskDto)));
    }
}
