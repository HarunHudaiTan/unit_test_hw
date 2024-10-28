package com.productManagement.demo.request;

import com.productManagement.demo.model.Category;

import java.math.BigDecimal;
import java.util.Set;

public record CreateProductRequest(
         String name,
         String description,
         Double price,
         Integer stockQuantity



){

}