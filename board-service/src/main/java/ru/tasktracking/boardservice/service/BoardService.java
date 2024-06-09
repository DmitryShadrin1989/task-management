package ru.tasktracking.boardservice.service;

import ru.tasktracking.boardservice.domain.Board;
import ru.tasktracking.boardservice.domain.User;
import ru.tasktracking.boardservice.dto.BoardDto;

import java.util.List;

public interface BoardService {

    List<Board> findAll();

    Board findById(String id);

    Board update(BoardDto boardDto);

    Board insert(BoardDto boardDto);

    List<Board> findByExecutorsId(String executorId);

    List<User> getExecutorsOfBoard(String boardId);
}
