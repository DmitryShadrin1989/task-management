package ru.tasktracking.taskservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.tasktracking.taskservice.filter.TaskBoardFilter;
import ru.tasktracking.taskservice.service.TaskService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TaskRestControllerFilterTest {

    @Mock
    private TaskService taskService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TaskRestController(taskService)).build();
    }

    @Test
    void getListTasks_shouldUseFilteredPathForExecutorId() throws Exception {
        when(taskService.getFilteredTasksForBoard(argThat(filter ->
                "board-1".equals(filter.boardId())
                        && filter.executorId().equals(Optional.of("executor-1"))
        ))).thenReturn(List.of());

        mockMvc.perform(get("/api/task")
                        .param("boardId", "board-1")
                        .param("executorId", "executor-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(taskService).getFilteredTasksForBoard(argThat(filter ->
                "board-1".equals(filter.boardId())
                        && filter.executorId().equals(Optional.of("executor-1"))
        ));
    }

    @Test
    void getListTasks_shouldCombineAuthorAndExecutorFilters() throws Exception {
        when(taskService.getFilteredTasksForBoard(argThat(filter ->
                filter.authorId().equals(Optional.of("author-1"))
                        && filter.executorId().equals(Optional.of("executor-1"))
        ))).thenReturn(List.of());

        mockMvc.perform(get("/api/task")
                        .param("boardId", "board-1")
                        .param("authorId", "author-1")
                        .param("executorId", "executor-1"))
                .andExpect(status().isOk());

        verify(taskService).getFilteredTasksForBoard(argThat(filter ->
                filter.authorId().equals(Optional.of("author-1"))
                        && filter.executorId().equals(Optional.of("executor-1"))
        ));
    }

    @Test
    void getListTasks_shouldParseReviewerUnassignedSentinel() throws Exception {
        when(taskService.getFilteredTasksForBoard(argThat(TaskBoardFilter::reviewerUnassigned)))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/task")
                        .param("boardId", "board-1")
                        .param("reviewerId", "UNASSIGNED"))
                .andExpect(status().isOk());

        verify(taskService).getFilteredTasksForBoard(argThat(filter ->
                filter.reviewerUnassigned() && filter.reviewerId().isEmpty()
        ));
    }

    @Test
    void getListTasks_shouldReturnEmptyArrayWhenNoMatches() throws Exception {
        when(taskService.getFilteredTasksForBoard(argThat(filter ->
                filter.authorId().equals(Optional.of("missing-author"))
        ))).thenReturn(List.of());

        mockMvc.perform(get("/api/task")
                        .param("boardId", "board-1")
                        .param("authorId", "missing-author")
                        .param("executorId", "missing-executor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getListTasks_shouldUseBoardPathWithoutFilters() throws Exception {
        when(taskService.getListOfTasksForBoard("board-1")).thenReturn(List.of());

        mockMvc.perform(get("/api/task").param("boardId", "board-1"))
                .andExpect(status().isOk());

        verify(taskService).getListOfTasksForBoard("board-1");
    }
}
