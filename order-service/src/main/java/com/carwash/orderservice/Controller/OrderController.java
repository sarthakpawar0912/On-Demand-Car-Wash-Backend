package com.carwash.orderservice.Controller;

import com.carwash.orderservice.DTO.OrderRequest;
import com.carwash.orderservice.Entity.Order;
import com.carwash.orderservice.Enum.OrderStatus;
import com.carwash.orderservice.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    // ✅ CREATE
    @PostMapping("/create")
    public Order createOrder(@RequestBody OrderRequest req,
                             @RequestHeader("X-User-Email") String email) {
        return service.createOrder(req, email);
    }

    // ✅ MY ORDERS
    @GetMapping("/my")
    public List<Order> myOrders(@RequestHeader("X-User-Email") String email) {
        return service.getUserOrders(email);
    }

    // ✅ ASSIGN
    @PutMapping("/assign/{id}")
    public Order assignOrder(@PathVariable Long id,
                             @RequestParam Long washerId,
                             @RequestParam String scheduledAt,
                             @RequestHeader("X-User-Email") String email) {
        return service.assignOrder(id, washerId, scheduledAt);
    }

    // ✅ STATUS
    @PutMapping("/status/{id}")
    public Order updateStatus(@PathVariable Long id,
                              @RequestParam OrderStatus status,
                              @RequestHeader("X-User-Email") String email) {
        return service.updateStatus(id, status);
    }

    // ✅ DELETE
    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id,
                              @RequestHeader("X-User-Email") String email) {
        service.deleteOrder(id, email);
        return "Order deleted successfully";
    }

    // ✅ TRACK
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return service.getOrderById(id);
    }

    // ✅ PAY
    @PostMapping("/pay/{orderId}")
    public String pay(@PathVariable Long orderId,
                      @RequestParam Double amount,
                      @RequestHeader("X-User-Email") String email) {
        return service.payForOrder(orderId, amount, email);
    }
}
