package ru.tasktracking.pageservice.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorMessageRestApi {

    private LocalDateTime timestamp;

    private int errorCode;

    private String message;
}