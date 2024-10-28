package com.productManagement.demo.request;

import com.productManagement.demo.model.Product;
import com.productManagement.demo.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.Set;

public record CreateOrderRequest(
        String customerName,
        Double orderAmount,
        OrderStatus orderStatus,
        Set<Product> products

) {
}
