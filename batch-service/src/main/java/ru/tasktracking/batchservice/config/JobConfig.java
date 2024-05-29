package ru.tasktracking.batchservice.config;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    public static final String JOB_NAME = "dataInitJob";

    private final JobRepository jobRepository;

    @Bean
    public Job dataInitJob(TaskletStep removeAllUsers,
                           Step migrateUsersStep,
                           TaskletStep removeAllBoards,
                           Step migrateBoardsStep,
                           TaskletStep removeAllTasks,
                           Step migrateTasksStep) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(removeAllUsers)
                .next(migrateUsersStep)
                .next(removeAllBoards)
                .next(migrateBoardsStep)
                .next(removeAllTasks)
                .next(migrateTasksStep)
                .build();
    }
}
