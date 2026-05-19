package ru.tasktracking.taskservice.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;

final class MongoDbRefCriteria {

    private MongoDbRefCriteria() {
    }

    static Criteria idEquals(String refField, String id) {
        if (ObjectId.isValid(id)) {
            return Criteria.where(refField + ".$id").is(new ObjectId(id));
        }
        return Criteria.where(refField + ".$id").is(id);
    }
}
