package com.productManagement.demo.exception;

public class InsufficientStockException  extends RuntimeException{
    public InsufficientStockException(String message) {
        super(message);
    }
}
