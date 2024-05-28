package ru.tasktracking.taskservice.exception;

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

    LocalDateTime timestamp;

    int errorCode;

    String message;
}
