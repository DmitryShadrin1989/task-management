package ru.tasktracking.taskservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ru.tasktracking.taskservice.domain.Task;
import ru.tasktracking.taskservice.filter.TaskBoardFilter;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Task> findByBoardFilter(TaskBoardFilter filter) {
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(MongoDbRefCriteria.idEquals("board", filter.boardId()));

        if (filter.authorUnassigned()) {
            criteriaList.add(unassignedFieldCriteria("author"));
        } else if (filter.authorId().isPresent()) {
            criteriaList.add(MongoDbRefCriteria.idEquals("author", filter.authorId().get()));
        }

        if (filter.executorId().isPresent()) {
            criteriaList.add(MongoDbRefCriteria.idEquals("executor", filter.executorId().get()));
        }

        if (filter.reviewerUnassigned()) {
            criteriaList.add(unassignedFieldCriteria("reviewer"));
        } else if (filter.reviewerId().isPresent()) {
            criteriaList.add(MongoDbRefCriteria.idEquals("reviewer", filter.reviewerId().get()));
        }

        Criteria combined = new Criteria().andOperator(criteriaList.toArray(Criteria[]::new));
        return mongoTemplate.find(new Query(combined), Task.class);
    }

    private Criteria unassignedFieldCriteria(String field) {
        return new Criteria().orOperator(
                Criteria.where(field).is(null),
                Criteria.where(field).exists(false)
        );
    }
}
