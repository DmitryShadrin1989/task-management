package ru.task.management.mongodb.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.task.management.domain.Board;
import ru.task.management.domain.Task;
import ru.task.management.domain.TaskStatus;
import ru.task.management.domain.User;
import ru.task.management.repository.BoardRepository;
import ru.task.management.repository.TaskRepository;
import ru.task.management.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@ChangeLog
public class InitMongoDBDataChangeLog {

    private User user_1;

    private User user_2;

    private User user_3;

    private User user_4;

    private Board board_1;

    private Board board_2;

    private Task task_1;

    private Task task_2;

    private Task task_3;

    private Task task_4;

    private Task task_5;

    @ChangeSet(order = "000", id = "dropDB", author = "DShadrin", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initUsers", author = "DShadrin", runAlways = true)
    public void initUsers(UserRepository repository) {
        user_1 = repository.save(new User(
                null,
                "user_1",
                "password_1"));
        user_2 = repository.save(new User(
                null,
                "user_2",
                "password_2"));
        user_3 = repository.save(new User(
                null,
                "user_3",
                "password_3"));
        user_4 = repository.save(new User(
                null,
                "user_4",
                "password_4"));
    }

    @ChangeSet(order = "002", id = "initBoards", author = "DShadrin", runAlways = true)
    public void initBoards(BoardRepository repository) {
        board_1 = repository.save(new Board(
                null,
                "board_1",
                user_1,
                List.of(user_1, user_2, user_3)));

        board_2 = repository.save(new Board(
                null,
                "board_2",
                user_3,
                List.of(user_3, user_4)));
    }

    @ChangeSet(order = "003", id = "initTasks", author = "DShadrin", runAlways = true)
    public void initTasks(TaskRepository repository) {
        task_1 = repository.save(new Task(
                null,
                "task_1",
                "describe_1",
                board_1,
                user_1,
                user_1,
                user_2,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 1),
                LocalDate.of(2024, 3, 1),
                TaskStatus.COMPLETED
                ));

        task_2 = repository.save(new Task(
                null,
                "task_2",
                "describe_2",
                board_1,
                user_1,
                user_2,
                user_1,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 1),
                LocalDate.of(2024, 3, 1),
                TaskStatus.COMPLETED
        ));

        task_3 = repository.save(new Task(
                null,
                "task_3",
                "describe_3",
                board_1,
                user_1,
                user_3,
                user_2,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 1),
                null,
                TaskStatus.IN_WORK
        ));

        task_4 = repository.save(new Task(
                null,
                "task_4",
                "describe_4",
                board_2,
                user_3,
                user_3,
                user_4,
                LocalDate.of(2024, 2, 1),
                LocalDate.of(2024, 3, 1),
                null,
                TaskStatus.ON_CHECKING
        ));

        task_5 = repository.save(new Task(
                null,
                "task_5",
                "describe_5",
                board_2,
                user_3,
                user_4,
                user_3,
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 4, 1),
                null,
                TaskStatus.IN_QUEUE
        ));
    }
}
