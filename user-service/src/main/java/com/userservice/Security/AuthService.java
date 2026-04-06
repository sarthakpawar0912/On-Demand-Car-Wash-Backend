package com.userservice.Security;

import com.userservice.DTO.*;
import com.userservice.ENtity.User;
import com.userservice.Enum.Roles;
import com.userservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    // 🔥 REGISTER METHOD
    public AuthResponse register(RegisterRequest req) {

        // ✅ EMAIL ALREADY EXISTS CHECK
        if (repo.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // 🔥 ONLY ONE ADMIN LOGIC
        if (req.getRole().equalsIgnoreCase("ADMIN")) {
            if (repo.existsByRole(Roles.ADMIN)) {
                throw new RuntimeException("Admin already exists");
            }
        }

        // ✅ CREATE USER
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setPassword(encoder.encode(req.getPassword()));

        // ROLE SET
        user.setRole(Roles.valueOf(req.getRole().toUpperCase()));

        repo.save(user);

        // 🔐 GENERATE TOKEN
        String token = jwt.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(
                "User registered successfully",
                "Bearer " + token,
                user.getEmail(),
                user.getRole().name()
        );
    }

    // 🔐 LOGIN METHOD
    public AuthResponse login(LoginRequest req) {

        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwt.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(
                "Login successful",
                "Bearer " + token,
                user.getEmail(),
                user.getRole().name()
        );
    }
}