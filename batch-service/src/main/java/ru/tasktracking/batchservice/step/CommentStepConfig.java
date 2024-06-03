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
import ru.tasktracking.batchservice.converts.CommentConverter;
import ru.tasktracking.batchservice.domain.Comment;
import ru.tasktracking.batchservice.dto.CommentCsvDto;
import ru.tasktracking.batchservice.repository.CommentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
@RequiredArgsConstructor
public class CommentStepConfig {

    private static final int CHUNK_SIZE = 3;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final MongoTemplate mongoTemplate;

    private final CommentConverter commentConverter;

    private final CommentRepository commentRepository;

    private final AppProperties appProperties;

    @Bean
    public FlatFileItemReader<CommentCsvDto> commentItemReader() {
        return new FlatFileItemReaderBuilder<CommentCsvDto>()
                .name("commentItemReader")
                .resource(new ClassPathResource(appProperties.getCommentInitFile()))
                .delimited()
                .names("id", "authorId", "taskId", "creationDate", "comment")
                .fieldSetMapper(fieldSet -> {
                    CommentCsvDto commentCsvDto = new CommentCsvDto();
                    commentCsvDto.setId(fieldSet.readLong("id"));
                    commentCsvDto.setAuthorId(fieldSet.readLong("authorId"));
                    commentCsvDto.setTaskId(fieldSet.readLong("taskId"));
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    commentCsvDto.setCreationDate(LocalDateTime.of(LocalDate.parse(
                            fieldSet.readString("creationDate"), dateTimeFormatter), LocalTime.NOON));
                    commentCsvDto.setComment(fieldSet.readString("comment"));
                    return commentCsvDto;
                }).build();
    }

    @Bean
    public ItemProcessor<CommentCsvDto, Comment> commentItemProcessor() {
        return commentConverter::convertToDomain;
    }

    @Bean
    public MongoItemWriter<Comment> commentItemWriter() {
        return new MongoItemWriterBuilder<Comment>()
                .collection("comments")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public TaskletStep removeAllComments(JobRepository jobRepository) {
        return new StepBuilder("removeComments", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(((contribution, chunkContext) -> {
                    commentRepository.deleteAll();
                    return RepeatStatus.FINISHED;
                }), platformTransactionManager)
                .build();
    }

    @Bean
    public Step migrateCommentsStep(FlatFileItemReader<CommentCsvDto> commentItemReader,
                                 ItemProcessor<CommentCsvDto, Comment> commentItemProcessor,
                                 MongoItemWriter<Comment> commentItemWriter) {
        return new StepBuilder("migrateCommentsStep", jobRepository)
                .<CommentCsvDto, Comment>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(commentItemReader)
                .processor(commentItemProcessor)
                .writer(commentItemWriter)
                .build();
    }
}