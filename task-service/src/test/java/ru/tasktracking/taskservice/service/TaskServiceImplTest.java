package ru.tasktracking.taskservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tasktracking.taskservice.domain.Board;
import ru.tasktracking.taskservice.domain.Task;
import ru.tasktracking.taskservice.domain.TaskStatus;
import ru.tasktracking.taskservice.domain.User;
import ru.tasktracking.taskservice.repository.TaskRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private BoardService boardService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void shouldFilterByAndCombination() {
        when(taskRepository.findAllByBoardId("board-1"))
                .thenReturn(List.of(
                        task("task-1", "board-1", "author-1", "exec-1", "review-1", "inWork"),
                        task("task-2", "board-1", "author-1", "exec-2", "review-1", "inQueue"),
                        task("task-3", "board-1", "author-2", "exec-1", "review-2", "inWork")
                ));

        List<Task> filteredTasks = taskService.getListOfTasksForBoard(
                "board-1",
                "author-1",
                null,
                null,
                "inWork"
        );

        assertThat(filteredTasks)
                .hasSize(1)
                .extracting(Task::getId)
                .containsExactly("task-1");
    }

    @Test
    void shouldReturnAllTasksWhenOptionalFiltersAreMissing() {
        when(taskRepository.findAllByBoardId("board-1"))
                .thenReturn(List.of(
                        task("task-1", "board-1", "author-1", "exec-1", "review-1", "open"),
                        task("task-2", "board-1", "author-2", "exec-2", "review-2", "completed")
                ));

        List<Task> filteredTasks = taskService.getListOfTasksForBoard(
                "board-1",
                null,
                null,
                null,
                null
        );

        assertThat(filteredTasks)
                .hasSize(2)
                .extracting(Task::getId)
                .containsExactly("task-1", "task-2");
    }

    @Test
    void shouldReturnEmptyListWhenNoTasksMatchFilters() {
        when(taskRepository.findAllByBoardId("board-1"))
                .thenReturn(List.of(
                        task("task-1", "board-1", "author-1", "exec-1", "review-1", "open"),
                        task("task-2", "board-1", "author-2", "exec-2", "review-2", "completed")
                ));

        List<Task> filteredTasks = taskService.getListOfTasksForBoard(
                "board-1",
                "author-1",
                null,
                null,
                "inWork"
        );

        assertThat(filteredTasks).isEmpty();
    }

    private Task task(String id,
                      String boardId,
                      String authorId,
                      String executorId,
                      String reviewerId,
                      String statusValue) {
        return new Task(
                id,
                "Task " + id,
                "Describe",
                new Board(boardId, "Board"),
                new User(authorId, "Author"),
                new User(executorId, "Executor"),
                new User(reviewerId, "Reviewer"),
                null,
                null,
                null,
                TaskStatus.findByValue(statusValue)
        );
    }
}
