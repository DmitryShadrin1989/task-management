package ru.tasktracking.gatewayservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.tasktracking.gatewayservice.domain.User;

@FeignClient(name = "user-service")
public interface UserFeignService {

    @GetMapping(value = "/api/user/username/{username}")
    User findUserByUsernameIgnoreCase(@PathVariable String username);
}
