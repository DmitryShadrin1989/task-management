package ru.task.management.controller;

import dto.BoardDto;
import dto.TaskDto;
import dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.task.management.domain.Board;
import ru.task.management.service.BoardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardService boardService;

    @GetMapping("/api/task-management/board")
    public ResponseEntity<List<Board>> getListBoards() {
        return ResponseEntity.ok(boardService.findAll());
    }

    @GetMapping("/api/task-management/board/{id}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable String id) {
        return ResponseEntity.ok(new BoardDto(boardService.findById(id)));
    }

    @PutMapping("/api/task-management/board/{id}")
    public ResponseEntity<BoardDto> updateBoard(@PathVariable String id, @RequestBody BoardDto boardDto) {
        return ResponseEntity.ok(new BoardDto(boardService.update(boardDto)));
    }

//    @PatchMapping("/api/task-management/board/{id}")
//    public ResponseEntity<BoardDto> updatePerformersOnBoard(@PathVariable String id, @RequestBody List<UserDto> userDtoList) {
//        return ResponseEntity.ok(new BoardDto(boardService.updatePerformersOnBoard(id, userDtoList)));
//    }

    @PostMapping("/api/task-management/board")
    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto boardDto) {
        return ResponseEntity.ok(new BoardDto(boardService.insert(boardDto)));
    }
}
