package ru.tasktracking.boardservice.controller;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tasktracking.boardservice.domain.Board;
import ru.tasktracking.boardservice.dto.BoardDto;
import ru.tasktracking.boardservice.dto.UserDto;
import ru.tasktracking.boardservice.service.BoardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardService boardService;

    @GetMapping("/api/board")
    public ResponseEntity<List<Board>> getListBoards(@RequestParam @Nullable String executorId) {
        if (executorId != null) {
            return ResponseEntity.ok(boardService.findByExecutorsId(executorId));
        }
        return ResponseEntity.ok(boardService.findAll());
    }

    @GetMapping("/api/board/{id}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable String id) {
        return ResponseEntity.ok(new BoardDto(boardService.findById(id)));
    }

    @GetMapping("/api/board/{boardId}/user")
    public ResponseEntity<List<UserDto>> getExecutorsOfBoard(@PathVariable String boardId) {
        return ResponseEntity.ok(UserDto.toDtoList(boardService.getExecutorsOfBoard(boardId)));
    }

    @PutMapping("/api/board/{id}")
    public ResponseEntity<BoardDto> updateBoard(@PathVariable String id, @RequestBody BoardDto boardDto) {
        return ResponseEntity.ok(new BoardDto(boardService.update(boardDto)));
    }

    @PostMapping("/api/board")
    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto boardDto) {
        return ResponseEntity.ok(new BoardDto(boardService.insert(boardDto)));
    }
}
