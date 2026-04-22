package ru.tasktracking.taskservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.tasktracking.taskservice.service.TaskService;

import java.util.List;

import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TaskRestControllerTest {

    @Mock
    private TaskService taskService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TaskRestController(taskService)).build();
    }

    @Test
    void shouldReturn200AndEmptyArrayWhenNoTasksMatchFilters() throws Exception {
        when(taskService.getListOfTasksForBoard("board-1", null, null, null, null))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/task")
                        .param("boardId", "board-1")
                        .param("authorId", "")
                        .param("status", "all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(taskService).getListOfTasksForBoard(
                eq("board-1"),
                isNull(),
                isNull(),
                isNull(),
                isNull()
        );
    }

    @Test
    void shouldReturnAllTasksWhenBoardIdIsMissing() throws Exception {
        when(taskService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/task")
                        .param("authorId", "author-1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(taskService).findAll();
    }

    @Test
    void shouldNormalizeOptionalFiltersBeforeCallingService() throws Exception {
        when(taskService.getListOfTasksForBoard("board-1", "author-1", null, null, null))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/task")
                        .param("boardId", "board-1")
                        .param("authorId", "author-1")
                        .param("executorId", "   ")
                        .param("reviewerId", "all")
                        .param("status", ""))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(taskService).getListOfTasksForBoard(
                eq("board-1"),
                eq("author-1"),
                isNull(),
                isNull(),
                isNull()
        );
    }
}
