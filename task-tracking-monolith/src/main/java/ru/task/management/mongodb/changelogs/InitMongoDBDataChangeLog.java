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

    private Board board_3;

    private Task task_1;

    private Task task_2;

    private Task task_3;

    private Task task_4;

    private Task task_5;

    @ChangeSet(order = "000", id = "dropDB", author = "DShadrin", runAlways = true)
    public void dropDB(MongoDatabase database) {
//        database.drop();
    }

    @ChangeSet(order = "001", id = "initUsers", author = "DShadrin", runAlways = true)
    public void initUsers(UserRepository repository) {
        repository.deleteAll();

        user_1 = repository.save(new User(
                null,
                "Shadrin Dmitry",
                "$2a$10$ogtqiPDDOMSS10tsOWovruSJn1ygG82KyXmRWS4nXyvswtxkoCIbi"));
        user_2 = repository.save(new User(
                null,
                "Neil deGrasse Tyson",
                "$2a$10$ogtqiPDDOMSS10tsOWovruSJn1ygG82KyXmRWS4nXyvswtxkoCIbi"));
        user_3 = repository.save(new User(
                null,
                "Carl Sagan",
                "$2a$10$ogtqiPDDOMSS10tsOWovruSJn1ygG82KyXmRWS4nXyvswtxkoCIbi"));
        user_4 = repository.save(new User(
                null,
                "Sergey Popov",
                "$2a$10$ogtqiPDDOMSS10tsOWovruSJn1ygG82KyXmRWS4nXyvswtxkoCIbi"));
    }

    @ChangeSet(order = "002", id = "initBoards", author = "DShadrin", runAlways = true)
    public void initBoards(BoardRepository repository) {
        repository.deleteAll();

        board_1 = repository.save(new Board(
                null,
                "Studying the bodies of the solar system",
                user_1,
                List.of(user_1, user_2, user_3)));

        board_2 = repository.save(new Board(
                null,
                "Studying black holes",
                user_3,
                List.of(user_3, user_4)));
        board_3 = repository.save(new Board(
                null,
                "Learning the spring boot framework",
                user_1,
                List.of(user_1, user_2, user_3, user_4)));
    }

    @ChangeSet(order = "003", id = "initTasks", author = "DShadrin", runAlways = true)
    public void initTasks(TaskRepository repository) {
        repository.deleteAll();

        task_1 = repository.save(new Task(
                null,
                "The search for life on Mars",
                "It is necessary to test all theories on the search for life on the planet Mars",
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
                "Preparation of an expedition to Jupiter's moon Europa",
                "It is necessary to prepare everything necessary for an expedition to Jupiter's moon Europa",
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
                "Return Pluto to the status of a planet",
                "Try to return Pluto to the status of a planet",
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
                "Make a list of the largest black holes",
                "It is necessary to make a list of the largest black holes",
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
                "To study the effects when approaching large black holes",
                "It is necessary to study the effects when approaching large black holes",
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
