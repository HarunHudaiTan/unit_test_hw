package com.productManagement.demo.controller;


import com.productManagement.demo.model.Orders;
import com.productManagement.demo.model.enums.OrderStatus;
import com.productManagement.demo.request.CreateOrderRequest;
import com.productManagement.demo.request.UpdateOrderRequest;
import com.productManagement.demo.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders(Orders order) {
        return ResponseEntity.ok(orderService.getAllOrders());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<Orders> createOrder(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @RequestBody UpdateOrderRequest request) {
        return ResponseEntity.ok(orderService.updateOrder(id, request));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/checkStatus")
    public ResponseEntity<OrderStatus> checkOrderStatus(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.checkOrderStatus(id));
    }

    @PostMapping("/cancelOrder/{id}")
    public ResponseEntity<Orders> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }


}
