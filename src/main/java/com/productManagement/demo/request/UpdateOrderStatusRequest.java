package com.productManagement.demo.request;

import com.productManagement.demo.model.enums.OrderStatus;

public record UpdateOrderStatusRequest(
        OrderStatus orderStatus
) {
}
