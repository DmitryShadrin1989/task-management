package ru.task.management.service;

import dto.BoardDto;
import dto.UserDto;
import ru.task.management.domain.Board;

import java.util.List;

public interface BoardService {

    List<Board> findAll();

    Board findById(String id);

    Board update(BoardDto boardDto);

    Board insert(BoardDto boardDto);

    Board updatePerformersOnBoard(String id, List<UserDto> userDtoList);
}
