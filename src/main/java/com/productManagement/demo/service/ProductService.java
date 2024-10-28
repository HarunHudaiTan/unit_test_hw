package com.productManagement.demo.service;


import com.productManagement.demo.exception.ProductNotFoundException;
import com.productManagement.demo.model.Category;
import com.productManagement.demo.model.Product;
import com.productManagement.demo.repository.ProductRepository;
import com.productManagement.demo.request.CreateProductRequest;
import com.productManagement.demo.request.UpdateCategoryRequest;
import com.productManagement.demo.request.UpdateProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return findProductById(id);
    }


    private Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Product Not Found By id"));
    }


    public Product createProduct(CreateProductRequest request) {

        Product product = new Product(request.name(),
                request.description(),
                request.price(),
                request.stockQuantity());

        return productRepository.save(product);

    }

    public boolean isProductInStock(Long id) {
        Product product = findProductById(id);
        return product.getStockQuantity() > 0;

    }

    public Product updateProduct(Long id, UpdateProductRequest request) {
        Product product = findProductById(id);
        Product updatedProduct = new Product(product.getId(),
                request.name(),
                request.description(),
                request.price(),
                request.stockQuantity());
        return productRepository.save(updatedProduct);
    }


}
