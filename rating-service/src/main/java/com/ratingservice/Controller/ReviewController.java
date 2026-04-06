package com.ratingservice.Controller;

import com.ratingservice.Entity.Review;
import com.ratingservice.Services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    // ✅ ADD REVIEW
    @PostMapping("/add")
    public Review addReview(@RequestBody Review review,
                            @RequestHeader("X-User-Email") String email) {
        return service.addReview(review, email);
    }

    // ✅ MY REVIEWS
    @GetMapping("/my")
    public List<Review> myReviews(@RequestHeader("X-User-Email") String email) {
        return service.getMyReviews(email);
    }

    // ✅ GET BY ORDER (PUBLIC)
    @GetMapping("/order/{orderId}")
    public List<Review> getByOrder(@PathVariable Long orderId) {
        return service.getByOrder(orderId);
    }
}
