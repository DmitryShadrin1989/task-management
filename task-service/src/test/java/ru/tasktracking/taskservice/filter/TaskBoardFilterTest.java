package ru.tasktracking.taskservice.filter;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TaskBoardFilterTest {

    @Test
    void fromQuery_shouldParseUnassignedReviewer() {
        var filter = TaskBoardFilter.fromQuery("board-1", null, null, "UNASSIGNED");

        assertThat(filter.reviewerUnassigned()).isTrue();
        assertThat(filter.reviewerId()).isEmpty();
        assertThat(filter.hasFilters()).isTrue();
    }

    @Test
    void fromQuery_shouldParseUserIds() {
        var filter = TaskBoardFilter.fromQuery("board-1", "author-1", "executor-1", null);

        assertThat(filter.authorId()).contains("author-1");
        assertThat(filter.executorId()).contains("executor-1");
        assertThat(filter.authorUnassigned()).isFalse();
    }

    @Test
    void hasFilterParams_shouldDetectAnyFilter() {
        assertThat(TaskBoardFilter.hasFilterParams(null, "executor-1", null)).isTrue();
        assertThat(TaskBoardFilter.hasFilterParams("", "", "")).isFalse();
    }
}
