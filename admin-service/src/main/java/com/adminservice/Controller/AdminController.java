package com.adminservice.Controller;

import com.adminservice.Entity.ServicePlan;
import com.adminservice.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService service;

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access Denied: Admin only");
        }
    }

    @GetMapping("/users")
    public List<?> users(@RequestHeader("X-User-Role") String role) {
        checkAdmin(role);
        return service.getUsers();
    }

    @PostMapping("/plan")
    public ServicePlan create(@RequestBody ServicePlan plan,
                              @RequestHeader("X-User-Role") String role) {
        checkAdmin(role);
        return service.createPlan(plan);
    }

    @GetMapping("/plans")
    public List<ServicePlan> plans(@RequestHeader("X-User-Role") String role) {
        checkAdmin(role);
        return service.getPlans();
    }

    @GetMapping("/report")
    public String report(@RequestHeader("X-User-Role") String role) {
        checkAdmin(role);
        return service.getReport();
    }
}
