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
import ru.tasktracking.batchservice.converts.BoardConverter;
import ru.tasktracking.batchservice.domain.Board;
import ru.tasktracking.batchservice.dto.BoardCsvDto;
import ru.tasktracking.batchservice.repository.BoardRepository;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BoardStepConfig {

    private static final int CHUNK_SIZE = 3;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final MongoTemplate mongoTemplate;

    private final BoardConverter boardConverter;

    private final BoardRepository boardRepository;

    private final AppProperties appProperties;

    @Bean
    public FlatFileItemReader<BoardCsvDto> boardItemReader() {
        return new FlatFileItemReaderBuilder<BoardCsvDto>()
                .name("boardItemReader")
                .resource(new ClassPathResource(appProperties.getBoardInitFile()))
                .delimited()
                .names("id", "name", "ownerId", "executorIds")
                .fieldSetMapper(fieldSet -> {
                    BoardCsvDto boardCsvDto = new BoardCsvDto();
                    boardCsvDto.setId(fieldSet.readLong("id"));
                    boardCsvDto.setName(fieldSet.readString("name"));
                    boardCsvDto.setOwnerId(fieldSet.readLong("ownerId"));

                    String[] stringExecutorIds = fieldSet.readRawString("executorIds").split("\\|");
                    List<Long> executorIds = Arrays.stream(stringExecutorIds).map(Long::parseLong).toList();
                    boardCsvDto.setExecutorIds(executorIds);

                    return boardCsvDto;
                }).build();
    }

    @Bean
    public ItemProcessor<BoardCsvDto, Board> boardItemProcessor() {
        return boardConverter::convertToDomain;
    }

    @Bean
    public MongoItemWriter<Board> boardItemWriter() {
        return new MongoItemWriterBuilder<Board>()
                .collection("boards")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public TaskletStep removeAllBoards(JobRepository jobRepository) {
        return new StepBuilder("removeBoards", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(((contribution, chunkContext) -> {
                    boardRepository.deleteAll();
                    return RepeatStatus.FINISHED;
                }), platformTransactionManager)
                .build();
    }

    @Bean
    public Step migrateBoardsStep(FlatFileItemReader<BoardCsvDto> boardItemReader,
                                 ItemProcessor<BoardCsvDto, Board> boardItemProcessor,
                                 MongoItemWriter<Board> boardItemWriter) {
        return new StepBuilder("migrateBoardsStep", jobRepository)
                .<BoardCsvDto, Board>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(boardItemReader)
                .processor(boardItemProcessor)
                .writer(boardItemWriter)
                .build();
    }
}
