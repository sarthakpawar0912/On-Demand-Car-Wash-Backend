package com.notification_service.Service;
import com.notification_service.Entity.Notification;
import com.notification_service.Repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repo;

    public Notification save(String email, String message) {

        Notification notification = new Notification();
        notification.setEmail(email);
        notification.setMessage(message);
        notification.setType("EMAIL");
        notification.setCreatedAt(LocalDateTime.now());

        return repo.save(notification);
    }

    public List<Notification> getAll() {
        return repo.findAll();
    }
}