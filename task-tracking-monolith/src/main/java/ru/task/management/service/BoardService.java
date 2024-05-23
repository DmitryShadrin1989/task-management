package ru.task.management.service;

import ru.task.management.domain.User;
import ru.task.management.dto.BoardDto;
import ru.task.management.dto.UserDto;
import ru.task.management.domain.Board;

import java.util.List;

public interface BoardService {

    List<Board> findAll();

    Board findById(String id);

    Board update(BoardDto boardDto);

    Board insert(BoardDto boardDto);

    Board updatePerformersOnBoard(String id, List<UserDto> userDtoList);

    List<Board> findByExecutorsId(String executorId);

    List<User> getExecutorsOfBoard(String boardId);
}
