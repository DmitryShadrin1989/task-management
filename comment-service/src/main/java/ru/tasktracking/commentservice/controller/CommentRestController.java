package ru.tasktracking.commentservice.controller;

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
import ru.tasktracking.commentservice.dto.CommentDto;
import ru.tasktracking.commentservice.service.CommentService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

    @GetMapping("/api/comment")
    public ResponseEntity<List<CommentDto>> getListComments(@Nullable @RequestParam(name = "taskId") String taskId) {
        log.info("calling the method: getListComments");
        if (taskId != null) {
            return ResponseEntity.ok(CommentDto.toDtoList(commentService.getListOfCommentsForTaskId(taskId)));
        }
        return ResponseEntity.ok(CommentDto.toDtoList(commentService.findAll()));
    }

    @GetMapping("/api/comment/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable String id) {
        log.info("calling the method: getComment");
        return ResponseEntity.ok(new CommentDto(commentService.findById(id)));
    }

    @PutMapping("/api/comment/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable String id, @RequestBody CommentDto commentDto) {
        log.info("calling the method: updateComment");
        return ResponseEntity.ok(new CommentDto(commentService.update(commentDto)));
    }

    @PostMapping("/api/comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        log.info("calling the method: createComment");
        return ResponseEntity.ok(new CommentDto(commentService.insert(commentDto)));
    }
}
