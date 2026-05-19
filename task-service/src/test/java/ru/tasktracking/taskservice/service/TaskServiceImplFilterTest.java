package ru.tasktracking.taskservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tasktracking.taskservice.domain.Task;
import ru.tasktracking.taskservice.filter.TaskBoardFilter;
import ru.tasktracking.taskservice.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplFilterTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private BoardService boardService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void getFilteredTasksForBoard_shouldDelegateToRepositoryWhenFiltersPresent() {
        var filter = new TaskBoardFilter(
                "board-1",
                Optional.empty(),
                false,
                Optional.of("executor-1"),
                Optional.empty(),
                false
        );
        var tasks = List.of(new Task());
        when(taskRepository.findByBoardFilter(filter)).thenReturn(tasks);

        var result = taskService.getFilteredTasksForBoard(filter);

        assertThat(result).isEqualTo(tasks);
        verify(taskRepository).findByBoardFilter(filter);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void getFilteredTasksForBoard_shouldUseFindAllByBoardIdWhenNoFilters() {
        var filter = new TaskBoardFilter(
                "board-1",
                Optional.empty(),
                false,
                Optional.empty(),
                Optional.empty(),
                false
        );
        var tasks = List.of(new Task());
        when(taskRepository.findAllByBoardId("board-1")).thenReturn(tasks);

        var result = taskService.getFilteredTasksForBoard(filter);

        assertThat(result).isEqualTo(tasks);
        verify(taskRepository).findAllByBoardId("board-1");
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void getFilteredTasksForBoard_shouldSupportReviewerUnassignedFilter() {
        var filter = new TaskBoardFilter(
                "board-1",
                Optional.empty(),
                false,
                Optional.empty(),
                Optional.empty(),
                true
        );
        when(taskRepository.findByBoardFilter(filter)).thenReturn(List.of());

        var result = taskService.getFilteredTasksForBoard(filter);

        assertThat(result).isEmpty();
        verify(taskRepository).findByBoardFilter(filter);
    }
}
