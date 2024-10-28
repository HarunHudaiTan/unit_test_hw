package com.productManagement.demo.service;


import com.productManagement.demo.model.Orders;
import com.productManagement.demo.model.enums.OrderStatus;
import com.productManagement.demo.repository.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

//This is a separate class because Junit 4 does not have @ParameterizedTest annotation
public class ParameterizedTestForCheckOrderStatus {
    private OrderService service;
    private OrdersRepository orderRepository;

    @BeforeEach
    public void setUp() {
        orderRepository = mock(OrdersRepository.class);
        service = new OrderService(orderRepository);

    }

    @ParameterizedTest
    @EnumSource(OrderStatus.class)
    public void testCheckOrderStatusParameterized(OrderStatus status) {
        Orders order = new Orders(1L, "John Doe", LocalDateTime.now(), 100.0, status, Collections.emptySet());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderStatus result = service.checkOrderStatus(1L);

        assertEquals(status, result);
        verify(orderRepository).findById(1L);
    }

}
