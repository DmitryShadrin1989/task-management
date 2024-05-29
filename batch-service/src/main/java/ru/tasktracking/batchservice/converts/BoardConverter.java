package ru.tasktracking.batchservice.converts;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import ru.tasktracking.batchservice.domain.Board;
import ru.tasktracking.batchservice.dto.BoardCsvDto;

import java.util.HashMap;
import java.util.Map;

@Component
public class BoardConverter {

    private final Map<Long, Board> boardIdsMap;

    private final UserConverter userConverter;

    public BoardConverter(UserConverter userConverter) {
        this.boardIdsMap = new HashMap<>();
        this.userConverter = userConverter;
    }

    public String boardToString(Board board) {
        StringBuilder executors = new StringBuilder();
        board.getExecutors().forEach(e -> {
            if (!executors.isEmpty()) {
                executors.append(", ");
            }
            executors.append(userConverter.userToString(e));
        });
        return "Id: %s, Name: %s, Owner: %s, Executors: %s".formatted(board.getId(), board.getName(),
                userConverter.userToString(board.getOwner()), executors.toString());
    }

    public Board getBoard(Long boardCsvId) {
        return boardIdsMap.get(boardCsvId);
    }

    public Board convertToDomain(BoardCsvDto boardCsvDto) {
        String boardMongoId = new ObjectId().toString();
        Board board = new Board(boardMongoId, boardCsvDto.getName(), userConverter.getUser(boardCsvDto.getOwnerId()),
                boardCsvDto.getExecutorIds().stream()
                        .map(userConverter::getUser)
                        .toList());
        boardIdsMap.put(boardCsvDto.getId(), board);
        return board;
    }
}
