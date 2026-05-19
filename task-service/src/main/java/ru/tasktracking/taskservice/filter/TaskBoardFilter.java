package ru.tasktracking.taskservice.filter;

import java.util.Optional;

import static ru.tasktracking.taskservice.filter.TaskFilterConstants.UNASSIGNED;

public record TaskBoardFilter(
        String boardId,
        Optional<String> authorId,
        boolean authorUnassigned,
        Optional<String> executorId,
        Optional<String> reviewerId,
        boolean reviewerUnassigned
) {

    public boolean hasFilters() {
        return authorUnassigned
                || reviewerUnassigned
                || authorId.isPresent()
                || executorId.isPresent()
                || reviewerId.isPresent();
    }

    public static boolean hasFilterParams(String authorId, String executorId, String reviewerId) {
        return isPresent(authorId) || isPresent(executorId) || isPresent(reviewerId);
    }

    public static TaskBoardFilter fromQuery(
            String boardId,
            String authorId,
            String executorId,
            String reviewerId
    ) {
        boolean authorUnassigned = UNASSIGNED.equals(authorId);
        boolean reviewerUnassigned = UNASSIGNED.equals(reviewerId);
        return new TaskBoardFilter(
                boardId,
                parseUserId(authorId, authorUnassigned),
                authorUnassigned,
                parseUserId(executorId, false),
                parseUserId(reviewerId, reviewerUnassigned),
                reviewerUnassigned
        );
    }

    private static Optional<String> parseUserId(String raw, boolean unassignedSentinel) {
        if (raw == null || raw.isBlank() || unassignedSentinel || UNASSIGNED.equals(raw)) {
            return Optional.empty();
        }
        return Optional.of(raw.trim());
    }

    private static boolean isPresent(String value) {
        return value != null && !value.isBlank();
    }
}
