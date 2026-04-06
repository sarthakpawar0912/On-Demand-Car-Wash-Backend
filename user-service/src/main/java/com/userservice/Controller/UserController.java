package com.userservice.Controller;

import com.userservice.ENtity.User;
import com.userservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repo;

    // ✅ For Feign
    @GetMapping("/email")
    public User getUserByEmail(@RequestParam String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ✅ For Admin
    @GetMapping("/all")
    public Object getAllUsers() {
        return repo.findAll();
    }
}