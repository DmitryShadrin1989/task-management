package ru.tasktracking.boardservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "boards")
public class Board {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    @DBRef
    private User owner;

    @DBRef
    private List<User> executors;
}
