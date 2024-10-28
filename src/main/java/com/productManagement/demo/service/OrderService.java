package com.productManagement.demo.service;

import com.productManagement.demo.exception.OrderNotFoundException;
import com.productManagement.demo.model.Orders;
import com.productManagement.demo.model.Product;
import com.productManagement.demo.model.enums.OrderStatus;
import com.productManagement.demo.repository.OrdersRepository;
import com.productManagement.demo.request.CreateOrderRequest;
import com.productManagement.demo.request.UpdateOrderRequest;
import com.productManagement.demo.request.UpdateOrderStatusRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrdersRepository orderRepository;


    public OrderService(OrdersRepository ordersRepository) {
        this.orderRepository = ordersRepository;

    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    private Orders findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new OrderNotFoundException("Order not found by id"));
    }

    public Orders getOrderById(Long id) {
        return findOrderById(id);
    }

    public Orders createOrder(CreateOrderRequest request) {

        Orders order = new Orders(request.customerName(),
                request.orderAmount(),
                request.orderStatus(), request.products());
        return orderRepository.save(order);
    }

    public Orders updateOrder(Long id, UpdateOrderRequest request) {
        Orders order = findOrderById(id);
        Orders updatedOrder = new Orders(order.getId(),
                request.customerName(),
                order.getOrderDate(),
                request.orderAmount(),
                request.orderStatus(),
                request.products());
        return orderRepository.save(updatedOrder);


    }

    public OrderStatus checkOrderStatus(Long id) {
        Orders order = findOrderById(id);
        return order.getStatus();
    }

    public Orders cancelOrder(Long id) {
        Orders order = findOrderById(id);
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {

        orderRepository.deleteById(id);
    }


}

