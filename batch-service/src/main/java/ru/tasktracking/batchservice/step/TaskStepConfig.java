package ru.tasktracking.batchservice.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.tasktracking.batchservice.config.AppProperties;
import ru.tasktracking.batchservice.converts.TaskConverter;
import ru.tasktracking.batchservice.domain.Task;
import ru.tasktracking.batchservice.dto.TaskCsvDto;
import ru.tasktracking.batchservice.repository.TaskRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
@RequiredArgsConstructor
public class TaskStepConfig {

    private static final int CHUNK_SIZE = 3;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final MongoTemplate mongoTemplate;

    private final TaskConverter taskConverter;

    private final TaskRepository taskRepository;

    private final AppProperties appProperties;

    @Bean
    public FlatFileItemReader<TaskCsvDto> taskItemReader() {
        return new FlatFileItemReaderBuilder<TaskCsvDto>()
                .name("taskItemReader")
                .resource(new ClassPathResource(appProperties.getTaskInitFile()))
                .delimited()
                .names("id", "name", "describe", "boardId", "authorId", "executorId", "reviewerId", "creationDate",
                        "plannedCompletionDate", "actualCompletionDate", "taskStatus")
                .fieldSetMapper(fieldSet -> {
                    TaskCsvDto taskCsvDto = new TaskCsvDto();
                    taskCsvDto.setId(fieldSet.readLong("id"));
                    taskCsvDto.setName(fieldSet.readString("name"));
                    taskCsvDto.setDescribe(fieldSet.readString("describe"));
                    taskCsvDto.setBoardId(fieldSet.readLong("boardId"));
                    taskCsvDto.setAuthorId(fieldSet.readLong("authorId"));
                    taskCsvDto.setExecutorId(fieldSet.readLong("executorId"));
                    taskCsvDto.setReviewerId(fieldSet.readLong("reviewerId"));
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    taskCsvDto.setCreationDate(LocalDate.parse(
                            fieldSet.readString("creationDate"), dateTimeFormatter));
                    taskCsvDto.setPlannedCompletionDate(LocalDate.parse(
                            fieldSet.readString("plannedCompletionDate"), dateTimeFormatter));
                    taskCsvDto.setActualCompletionDate(LocalDate.parse(
                            fieldSet.readString("actualCompletionDate"), dateTimeFormatter));
                    taskCsvDto.setTaskStatus(fieldSet.readString("taskStatus"));

                    return taskCsvDto;
                }).build();
    }

    @Bean
    public ItemProcessor<TaskCsvDto, Task> taskItemProcessor() {
        return taskConverter::convertToDomain;
    }

    @Bean
    public MongoItemWriter<Task> taskItemWriter() {
        return new MongoItemWriterBuilder<Task>()
                .collection("tasks")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public TaskletStep removeAllTasks(JobRepository jobRepository) {
        return new StepBuilder("removeTasks", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(((contribution, chunkContext) -> {
                    taskRepository.deleteAll();
                    return RepeatStatus.FINISHED;
                }), platformTransactionManager)
                .build();
    }

    @Bean
    public Step migrateTasksStep(FlatFileItemReader<TaskCsvDto> taskItemReader,
                                 ItemProcessor<TaskCsvDto, Task> taskItemProcessor,
                                 MongoItemWriter<Task> taskItemWriter) {
        return new StepBuilder("migrateTasksStep", jobRepository)
                .<TaskCsvDto, Task>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(taskItemReader)
                .processor(taskItemProcessor)
                .writer(taskItemWriter)
                .build();
    }
}
