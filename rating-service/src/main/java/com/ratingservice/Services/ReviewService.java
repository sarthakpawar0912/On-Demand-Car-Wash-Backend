package com.ratingservice.Services;

import com.ratingservice.DTO.User;
import com.ratingservice.DTO.UserClient;
import com.ratingservice.Entity.Review;
import com.ratingservice.Repository.ReviewRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repo;
    private final UserClient userClient;

    // =========================
    // 🔥 CIRCUIT BREAKER
    // =========================

    @CircuitBreaker(name = "userService", fallbackMethod = "userFallback")
    public User getUser(String email) {
        return userClient.getUserByEmail(email);
    }

    public User userFallback(String email, Exception e) {
        System.out.println("🔥 USER SERVICE DOWN (Review)");

        User user = new User();
        user.setId(0L);
        user.setEmail("fallback@gmail.com");

        return user;
    }

    // =========================
    // ✅ ADD REVIEW
    // =========================

    public Review addReview(Review review, String email) {

        User user = getUser(email);

        if (user.getId() == 0L) {
            throw new RuntimeException("User service unavailable");
        }

        review.setUserId(user.getId());

        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new RuntimeException("Rating must be between 1-5");
        }

        review.setCreatedAt(LocalDateTime.now());

        return repo.save(review);
    }

    // =========================
    // ✅ GET BY ORDER
    // =========================

    public List<Review> getByOrder(Long orderId) {
        return repo.findByOrderId(orderId);
    }

    // =========================
    // ✅ GET MY REVIEWS
    // =========================

    public List<Review> getMyReviews(String email) {

        User user = getUser(email);

        if (user.getId() == 0L) {
            throw new RuntimeException("User service unavailable");
        }

        return repo.findByUserId(user.getId());
    }
}