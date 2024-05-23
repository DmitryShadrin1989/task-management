package ru.task.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.task.management.domain.User;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String id;

    private String username;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }

    public static List<UserDto> toDtoList(List<User> users) {
        return users.stream()
                .map(UserDto::new)
                .toList();
    }
}
