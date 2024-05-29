package ru.tasktracking.boardservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tasktracking.boardservice.domain.Board;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    private String id;

    @NotBlank
    private String name;

    @NotNull
    private UserDto owner;

    private List<UserDto> executors;

    public BoardDto(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.owner = new UserDto(board.getOwner());
        this.executors = board.getExecutors().stream()
                .map(UserDto::new)
                .toList();
    }

    public static List<BoardDto> toDtoList(List<Board> boards) {
        return boards.stream()
                .map(BoardDto::new)
                .toList();
    }
}
