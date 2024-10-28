package com.productManagement.demo.request;

import com.productManagement.demo.model.Product;
import com.productManagement.demo.model.enums.OrderStatus;

import java.util.Set;

public record UpdateOrderRequest(
        String customerName,
        Double orderAmount,
        OrderStatus orderStatus,
        Set<Product> products

) {
}
