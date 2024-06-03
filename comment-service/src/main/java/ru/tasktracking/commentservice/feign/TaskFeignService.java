package ru.tasktracking.commentservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.tasktracking.commentservice.domain.Task;

import java.util.Optional;

@FeignClient(name = "task-service")
public interface TaskFeignService {

    @GetMapping(value = "/api/task/{id}")
    Optional<Task> findById(@PathVariable String id);
}
