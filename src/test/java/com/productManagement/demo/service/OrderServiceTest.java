package com.productManagement.demo.service;

import com.productManagement.demo.exception.OrderNotFoundException;
import com.productManagement.demo.model.Orders;
import com.productManagement.demo.model.enums.OrderStatus;
import com.productManagement.demo.repository.OrdersRepository;
import com.productManagement.demo.request.CreateOrderRequest;
import com.productManagement.demo.request.UpdateOrderRequest;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderService service;
    private OrdersRepository orderRepository;

    @Before
    public void setUp() {
        orderRepository = mock(OrdersRepository.class);
        service = new OrderService(orderRepository);
    }

    @Test
    public void testGetAllOrders_ShouldReturnListOfOrders() {
        Orders order = new Orders(1L, "John Doe", LocalDateTime.now(), 100.0, OrderStatus.PENDING, Collections.emptySet());
        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<Orders> orders = service.getAllOrders();

        assertEquals(1, orders.size());
        assertEquals("John Doe", orders.get(0).getCustomerName());
        verify(orderRepository).findAll();
    }

    @Test
    public void testGetOrderById_WhenOrderIdExists_ShouldReturnOrder() {
        Orders order = new Orders(1L, "John Doe", LocalDateTime.now(), 100.0, OrderStatus.PENDING, Collections.emptySet());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Orders result = service.getOrderById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getCustomerName());
        verify(orderRepository).findById(1L);
    }

    @Test
    public void testGetOrderById_WhenOrderIdDoesNotExist_ShouldThrowOrderNotFoundException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> service.getOrderById(1L));
        verify(orderRepository).findById(1L);
    }

    @Test
    public void testCreateOrder_WhenOrderIsCreated_ShouldSaveOrder() {
        CreateOrderRequest request = new CreateOrderRequest("John Doe", 100.0, OrderStatus.PENDING, Collections.emptySet());
        Orders order = new Orders("John Doe", 100.0, OrderStatus.PENDING, Collections.emptySet());
        when(orderRepository.save(any(Orders.class))).thenReturn(order);

        Orders createdOrder = service.createOrder(request);

        assertNotNull(createdOrder);
        assertEquals("John Doe", createdOrder.getCustomerName());
        assertEquals(OrderStatus.PENDING, createdOrder.getStatus());
        verify(orderRepository).save(any(Orders.class));
    }

    @Test
    public void testUpdateOrder_WhenOrderIsUpdated_ShouldSaveOrder() {
        UpdateOrderRequest request = new UpdateOrderRequest("new name", 120.0, OrderStatus.PROCESSED, Collections.emptySet());

        Orders order = new Orders(1L, "old name", LocalDateTime.now(), 100.0, OrderStatus.PENDING, Collections.emptySet());

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Orders updatedOrder = new Orders("new name", 120.0, OrderStatus.PROCESSED, Collections.emptySet());

        when(orderRepository.save(any(Orders.class))).thenReturn(updatedOrder);

        Orders result = service.updateOrder(1L, request);

        assertNotNull(result);
        assertEquals("new name", result.getCustomerName());
        assertEquals(120.0, result.getOrderAmount());
        assertEquals(OrderStatus.PROCESSED, result.getStatus());

//         verify(orderRepository).findById(1L);
//         verify(orderRepository).save(any(Orders.class));
    }


    @Test
    public void testCancelOrder_ShouldCancelOrder() {
        Orders order = new Orders(1L, "John Doe", LocalDateTime.now(), 100.0, OrderStatus.PENDING, Collections.emptySet());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Orders canceledOrder = service.cancelOrder(1L);

        assertEquals(OrderStatus.CANCELLED, canceledOrder.getStatus());
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(order);
    }


}
