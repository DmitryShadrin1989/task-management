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
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.tasktracking.batchservice.config.AppProperties;
import ru.tasktracking.batchservice.converts.UserConverter;
import ru.tasktracking.batchservice.domain.User;
import ru.tasktracking.batchservice.dto.UserCsvDto;
import ru.tasktracking.batchservice.repository.UserRepository;

@Configuration
@RequiredArgsConstructor
public class UserStepConfig {

    private static final int CHUNK_SIZE = 3;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final MongoTemplate mongoTemplate;

    private final UserConverter userConverter;

    private final UserRepository userRepository;

    private final AppProperties appProperties;

    @Bean
    public FlatFileItemReader<UserCsvDto> userItemReader() {
        return new FlatFileItemReaderBuilder<UserCsvDto>()
                .name("userItemReader")
                .resource(new ClassPathResource(appProperties.getUserInitFile()))
                .delimited()
                .names("id", "username", "password")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(UserCsvDto.class);
                }}).build();
    }

    @Bean
    public ItemProcessor<UserCsvDto, User> userItemProcessor() {
        return userConverter::convertToDomain;
    }

    @Bean
    public MongoItemWriter<User> userItemWriter() {
        return new MongoItemWriterBuilder<User>()
                .collection("users")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public TaskletStep removeAllUsers(JobRepository jobRepository) {
        return new StepBuilder("removeUsers", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(((contribution, chunkContext) -> {
                    userRepository.deleteAll();
                    return RepeatStatus.FINISHED;
                }), platformTransactionManager)
                .build();
    }

    @Bean
    public Step migrateUsersStep(FlatFileItemReader<UserCsvDto> userItemReader,
                                 ItemProcessor<UserCsvDto, User> userItemProcessor,
                                 MongoItemWriter<User> userItemWriter) {
        return new StepBuilder("migrateAuthorsStep", jobRepository)
                .<UserCsvDto, User>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(userItemReader)
                .processor(userItemProcessor)
                .writer(userItemWriter)
                .build();
    }
}
