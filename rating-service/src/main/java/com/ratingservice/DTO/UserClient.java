package com.ratingservice.DTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "USER-SERVICE", configuration = FeignConfig.class)
public interface UserClient {

    @GetMapping("/user/email")
    User getUserByEmail(@RequestParam("email") String email);
}