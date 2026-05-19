package ru.tasktracking.taskservice.repository;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import static org.assertj.core.api.Assertions.assertThat;

class MongoDbRefCriteriaTest {

    @Test
    void idEquals_shouldUseObjectIdForHexId() {
        String id = "6a0711a32081ae6089826a74";
        Criteria criteria = MongoDbRefCriteria.idEquals("executor", id);
        Document query = new Query(criteria).getQueryObject();

        assertThat(query.get("executor.$id")).isEqualTo(new ObjectId(id));
    }

    @Test
    void idEquals_shouldKeepStringForNonObjectId() {
        String id = "not-an-object-id";
        Criteria criteria = MongoDbRefCriteria.idEquals("executor", id);
        Document query = new Query(criteria).getQueryObject();

        assertThat(query.get("executor.$id")).isEqualTo(id);
    }
}
