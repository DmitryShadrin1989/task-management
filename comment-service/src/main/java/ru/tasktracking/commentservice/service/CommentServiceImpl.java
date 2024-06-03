package ru.tasktracking.commentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tasktracking.commentservice.domain.Comment;
import ru.tasktracking.commentservice.dto.CommentDto;
import ru.tasktracking.commentservice.exception.EntityNotFoundException;
import ru.tasktracking.commentservice.feign.TaskFeignService;
import ru.tasktracking.commentservice.feign.UserFeignService;
import ru.tasktracking.commentservice.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final TaskFeignService taskFeignService;

    private final UserFeignService userFeignService;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getListOfCommentsForTaskId(String taskId) {
        return commentRepository.findAllByTaskId(taskId);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment findById(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %s not found".formatted(id)));
    }

    @Override
    @Transactional
    public Comment update(CommentDto commentDto) {
        return save(commentDto.getId(), commentDto.getAuthor().getId(), commentDto.getTask().getId(),
                commentDto.getCreationDate(), commentDto.getComment());
    }

    @Override
    @Transactional
    public Comment insert(CommentDto commentDto) {
        return save(null, commentDto.getAuthor().getId(), commentDto.getTask().getId(),
                LocalDateTime.now(), commentDto.getComment());
    }

    private Comment save(String id, String authorId, String taskId, LocalDateTime creationDate, String comment) {
        var author = userFeignService.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("User with id %s not found".formatted(authorId)));
        var task = taskFeignService.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task with id %s not found".formatted(taskId)));
        return commentRepository.save(new Comment(id, author, task, creationDate, comment));
    }
}
