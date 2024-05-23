package ru.task.management.service;

import ru.task.management.domain.User;
import ru.task.management.dto.BoardDto;
import ru.task.management.dto.UserDto;
import ru.task.management.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.task.management.domain.Board;
import ru.task.management.repository.BoardRepository;
import ru.task.management.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Board findById(String id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Board with id %s not found".formatted(id)));
    }

    @Override
    @Transactional
    public Board update(BoardDto boardDto) {
        return save(boardDto.getId(), boardDto.getName(), boardDto.getOwner().getId(),
                boardDto.getExecutors().stream()
                        .map(UserDto::getId)
                        .toList());
    }

    @Override
    @Transactional
    public Board updatePerformersOnBoard(String id, List<UserDto> userDtoList) {
        Board board = findById(id);
        return save(id, board.getName(), board.getOwner().getId(),
                userDtoList.stream()
                        .map(UserDto::getId)
                        .toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Board> findByExecutorsId(String executorId) {
        var executor = userRepository.findById(executorId)
                .orElseThrow(() -> new EntityNotFoundException("User with id %s not found".formatted(executorId)));
        return boardRepository.findAllByExecutorsContains(executor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getExecutorsOfBoard(String boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board with id %s not found".formatted(boardId)));
        return board.getExecutors();
    }

    @Override
    public Board insert(BoardDto boardDto) {
        return save(null, boardDto.getName(), boardDto.getOwner().getId(),
                boardDto.getExecutors().stream()
                        .map(UserDto::getId)
                        .toList());
    }

    private Board save(String id, String name, String ownerId, List<String> executorsIds) {
        var owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("User with id %s not found".formatted(ownerId)));
        var executors = executorsIds.stream().map(e -> userRepository.findById(e)
                .orElseThrow(() -> new EntityNotFoundException("User with id %s not found".formatted(e)))).toList();

        return boardRepository.save(new Board(id, name, owner, executors));
    }
}
