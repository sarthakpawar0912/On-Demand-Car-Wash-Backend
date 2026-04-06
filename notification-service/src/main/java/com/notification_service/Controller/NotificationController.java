package com.notification_service.Controller;

import com.notification_service.Entity.Notification;
import com.notification_service.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    // Get all notifications
    @GetMapping("/all")
    public List<Notification> getAll() {
        return service.getAll();
    }
}