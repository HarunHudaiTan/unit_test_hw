package com.productManagement.demo.model;

import com.productManagement.demo.model.enums.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private LocalDateTime orderDate;
    private Double orderAmount;
    private OrderStatus status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> product;

    public Orders(Long id, String customerName, LocalDateTime orderDate, Double orderAmount, OrderStatus status, Set<Product> product) {
        this.id = id;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.orderAmount = orderAmount;
        this.status = status;
        this.product = product;
    }


    public Orders(String customerName, Double orderAmount, OrderStatus status, Set<Product> product) {

        this.customerName = customerName;
        this.orderDate = LocalDateTime.now();
        this.orderAmount = orderAmount;
        this.status = status;
        this.product = product;
    }

    public Orders() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return Objects.equals(id, orders.id) && Objects.equals(customerName, orders.customerName) && Objects.equals(orderDate, orders.orderDate) && Objects.equals(orderAmount, orders.orderAmount) && Objects.equals(product, orders.product);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, customerName, orderDate, orderAmount, product);
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }


    public OrderStatus getStatus() {
        return status;
    }

    public void setOrderStatus(OrderStatus status) {
        this.status = status;
    }


}
