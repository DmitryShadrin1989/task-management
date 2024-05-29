package ru.tasktracking.taskservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.tasktracking.taskservice.domain.Board;

import java.util.Optional;

@FeignClient(name = "board-service")
public interface BoardFeignService {

    @GetMapping(value = "/api/board/{id}")
    Optional<Board> findById(@PathVariable String id);
}
