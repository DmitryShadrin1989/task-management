package ru.tasktracking.batchservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("app")
public class AppProperties {

    private String userInitFile;

    private String boardInitFile;

    private String taskInitFile;

    private String commentInitFile;
}
