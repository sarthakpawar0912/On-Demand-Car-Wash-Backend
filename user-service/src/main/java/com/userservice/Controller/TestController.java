package com.userservice.Controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/user/test")
    public String userTest(@RequestHeader("X-User-Email") String email) {
        return "Hello USER: " + email;
    }

    @GetMapping("/washer/test")
    public String washerTest(@RequestHeader("X-User-Email") String email) {
        return "Hello WASHER: " + email;
    }

    @GetMapping("/admin/test")
    public String adminTest(@RequestHeader("X-User-Email") String email) {
        return "Hello ADMIN: " + email;
    }
}
