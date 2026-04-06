package com.carwash.orderservice.Entity;

import com.carwash.orderservice.DTO.User;
import com.carwash.orderservice.configs.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "USER-SERVICE",
        configuration = FeignConfig.class   // 🔥 VERY IMPORTANT
)
public interface UserClient {

    // ✅ FIXED: use QUERY PARAM (matches user-service)
    @GetMapping("/user/email")
    User getUserByEmail(@RequestParam("email") String email);
}