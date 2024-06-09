package ru.tasktracking.batchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardCsvDto {

    private Long id;

    private String name;

    private Long ownerId;

    private List<Long> executorIds;
}
