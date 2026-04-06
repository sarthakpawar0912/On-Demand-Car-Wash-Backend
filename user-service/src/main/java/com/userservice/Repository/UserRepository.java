package com.userservice.Repository;

import com.userservice.ENtity.User;
import com.userservice.Enum.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByRole(Roles role);
}