package com.carwash.orderservice.Service;

import com.carwash.orderservice.DTO.*;
import com.carwash.orderservice.Entity.*;
import com.carwash.orderservice.Enum.OrderStatus;
import com.carwash.orderservice.Repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repo;
    private final PaymentClient paymentClient;
    private final CarClient carClient;
    private final UserClient userClient;

    private final OrderPublisher publisher;

    // =========================
    // 🔥 USER SERVICE (Retry + CB)
    // =========================

    @Retry(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "userFallback")
    public User getUser(String email) {
        return userClient.getUserByEmail(email);
    }

    public User userFallback(String email, Exception e) {
        System.out.println("🔥 USER SERVICE DOWN");

        User u = new User();
        u.setId(0L);
        u.setEmail("fallback@gmail.com");

        return u;
    }

    // =========================
    // 🔥 CAR SERVICE (Retry + CB)
    // =========================

    @Retry(name = "carService")
    @CircuitBreaker(name = "carService", fallbackMethod = "carFallback")
    public List<Car> getCars(Long userId) {
        return carClient.getCars(userId);
    }

    public List<Car> carFallback(Long userId, Exception e) {
        System.out.println("🔥 CAR SERVICE DOWN");
        return List.of();
    }

    // =========================
    // 🔥 PAYMENT SERVICE (Retry + CB)
    // =========================

    @Retry(name = "paymentService")
    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    public Payment createPayment(Long orderId, Double amount) {
        return paymentClient.pay(orderId, amount);
    }

    public Payment paymentFallback(Long orderId, Double amount, Exception e) {
        System.out.println("🔥 PAYMENT SERVICE DOWN");

        Payment p = new Payment();
        p.setStatus("FAILED_FALLBACK");

        return p;
    }

    // =========================
    // ✅ CREATE ORDER
    // =========================

    public Order createOrder(OrderRequest req, String email) {

        User user = getUser(email);
        Long userId = user.getId();

        List<Car> cars = getCars(userId);

        Car selectedCar = cars.stream()
                .filter(c -> c.getId().equals(Long.valueOf(req.getCarId())))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid car for this user"));

        Order order = new Order();
        order.setCarId(selectedCar.getId());
        order.setUserId(userId);
        order.setServiceType(req.getServiceType());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = repo.save(order);

        // 🔥 RabbitMQ EVENT
        try {
            OrderEvent event = new OrderEvent(
                    savedOrder.getId(),
                    email,
                    500.0,
                    "CREATED"
            );

            publisher.sendOrderEvent(event);

        } catch (Exception e) {
            System.out.println("⚠️ RabbitMQ Error: " + e.getMessage());
        }

        return savedOrder;
    }

    // =========================
    // ✅ GET USER ORDERS
    // =========================

    public List<Order> getUserOrders(String email) {
        User user = getUser(email);
        return repo.findByUserId(user.getId());
    }

    // =========================
    // ✅ ASSIGN ORDER
    // =========================

    public Order assignOrder(Long id, Long washerId, String scheduledAt) {

        Order order = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (washerId == null) {
            throw new RuntimeException("WasherId cannot be null");
        }

        try {
            order.setScheduledAt(LocalDateTime.parse(scheduledAt));
        } catch (Exception e) {
            throw new RuntimeException("Invalid date format. Use yyyy-MM-ddTHH:mm:ss");
        }

        order.setWasherId(washerId);
        order.setStatus(OrderStatus.ASSIGNED);

        return repo.save(order);
    }

    // =========================
    // ✅ UPDATE STATUS
    // =========================

    public Order updateStatus(Long id, OrderStatus status) {

        Order order = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);

        return repo.save(order);
    }

    // =========================
    // ✅ DELETE ORDER
    // =========================

    public void deleteOrder(Long id, String email) {

        User user = getUser(email);

        Order order = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUserId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized delete");
        }

        repo.deleteById(id);
    }

    // =========================
    // ✅ GET ORDER
    // =========================

    public Order getOrderById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // =========================
    // ✅ PAYMENT
    // =========================

    public String payForOrder(Long orderId, Double amount, String email) {

        User user = getUser(email);

        Order order = repo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUserId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized payment");
        }

        Payment payment = createPayment(orderId, amount);

        if ("SUCCESS".equals(payment.getStatus())) {
            order.setStatus(OrderStatus.COMPLETED);

            try {
                OrderEvent event = new OrderEvent(
                        order.getId(),
                        email,
                        amount,
                        "COMPLETED"
                );

                publisher.sendOrderEvent(event);

            } catch (Exception e) {
                System.out.println("⚠️ RabbitMQ Error: " + e.getMessage());
            }
        }

        repo.save(order);

        return "Payment " + payment.getStatus();
    }
}