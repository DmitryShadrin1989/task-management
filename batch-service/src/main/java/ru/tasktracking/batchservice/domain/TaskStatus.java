package ru.tasktracking.batchservice.domain;

import lombok.Getter;

@Getter
public enum TaskStatus {

    OPEN("open", "The task has been created, but has not yet been queued for execution"),

    IN_QUEUE("inQueue", "The task is in the queue for execution"),

    IN_WORK("inWork", "The task at work"),

    ON_CHECKING("onChecking", "The task is under review"),

    COMPLETED("completed", "The task is completed");

    private final String value;

    private final String describe;

    TaskStatus(String value, String describe) {
        this.value = value;
        this.describe = describe;
    }

    public static TaskStatus findByValue(String value) {
        for (TaskStatus status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No status constant with name " + value);
    }
}
