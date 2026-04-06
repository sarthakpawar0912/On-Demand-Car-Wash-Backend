package com.adminservice.Service;

import com.adminservice.DTO.UserClient;
import com.adminservice.Entity.ServicePlan;
import com.adminservice.Repository.ServicePlanRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ServicePlanRepository repo;
    private final UserClient userClient;

    // USERS (with Circuit Breaker + Retry)
    @Retry(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "usersFallback")
    public List<?> getUsers() {
        return userClient.getAllUsers();
    }

    public List<?> usersFallback(Exception e) {
        System.out.println("USER SERVICE DOWN (Admin): " + e.getMessage());
        return List.of();
    }

    // PLAN
    public ServicePlan createPlan(ServicePlan plan) {
        return repo.save(plan);
    }

    public List<ServicePlan> getPlans() {
        return repo.findAll();
    }

    // REPORT
    @Retry(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "reportFallback")
    public String getReport() {
        return "Total Users: " + userClient.getAllUsers().size();
    }

    public String reportFallback(Exception e) {
        return "Report unavailable: User service is down";
    }
}
