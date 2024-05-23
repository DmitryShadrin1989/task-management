package ru.task.management.controller;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.task.management.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.task.management.domain.Board;
import ru.task.management.dto.UserDto;
import ru.task.management.service.BoardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardService boardService;

    @GetMapping("/api/task-management/board")
    public ResponseEntity<List<Board>> getListBoards(@RequestParam @Nullable String executorId) {
        if (executorId != null) {
            return ResponseEntity.ok(boardService.findByExecutorsId(executorId));
        }
        return ResponseEntity.ok(boardService.findAll());
    }

    @GetMapping("/api/task-management/board/{id}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable String id) {
        return ResponseEntity.ok(new BoardDto(boardService.findById(id)));
    }

    @GetMapping("/api/task-management/board/{boardId}/user")
    public ResponseEntity<List<UserDto>> getExecutorsOfBoard(@PathVariable String boardId) {
        return ResponseEntity.ok(UserDto.toDtoList(boardService.getExecutorsOfBoard(boardId)));
    }

    @PutMapping("/api/task-management/board/{id}")
    public ResponseEntity<BoardDto> updateBoard(@PathVariable String id, @RequestBody BoardDto boardDto) {
        return ResponseEntity.ok(new BoardDto(boardService.update(boardDto)));
    }

    @PostMapping("/api/task-management/board")
    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto boardDto) {
        return ResponseEntity.ok(new BoardDto(boardService.insert(boardDto)));
    }
}
