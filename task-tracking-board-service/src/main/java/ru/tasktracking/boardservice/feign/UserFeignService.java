package ru.tasktracking.boardservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.tasktracking.boardservice.domain.User;

import java.util.Optional;

@FeignClient(name = "user-service")
public interface UserFeignService {

    @GetMapping(value = "/api/user/{id}")
    Optional<User> findById(@PathVariable String id);
}
