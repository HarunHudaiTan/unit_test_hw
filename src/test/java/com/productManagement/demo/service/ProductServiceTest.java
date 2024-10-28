package com.productManagement.demo.service;

import com.productManagement.demo.exception.ProductNotFoundException;

import com.productManagement.demo.model.Product;
import com.productManagement.demo.repository.ProductRepository;
import com.productManagement.demo.request.CreateProductRequest;

import com.productManagement.demo.request.UpdateProductRequest;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    private ProductService service;
    private ProductRepository productRepository;
    private ProductService mockService;


    @Before
    public void setUp() {

        productRepository = mock(ProductRepository.class);
        service = new ProductService(productRepository);  // Inject mock repository
    }

    @Test
    public void testGetAllProducts_ShouldReturnEmptyList_Initially() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        List<Product> products = service.getAllProducts();
        assertTrue(products.isEmpty(), "Product list should be empty initially.");

    }

    @Test
    public void testGetAllProducts_ShouldReturnListOfProducts() {
        Product product = new Product("name", "description", 100.0, 10);
        when(productRepository.findAll()).thenReturn(List.of(product));
        List<Product> products = service.getAllProducts();
        assertEquals(1, products.size(), "Product list should contain 1 product.");
        assertEquals(product, products.get(0));
        assertEquals("name", product.getName(), "Product name should be the same.");
    }


    @Test
    public void testGetProductById_WhenProductIdExists_ShouldReturnProduct() {

        Product product = new Product("name", "description", 100.0, 10);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = service.getProductById(1L);

        assertNotNull(result, "Product should not be null.");
        assertEquals(product, result, "Product ID should match.");

    }

    @Test
    public void testGetProductById_WhenProductIdDoesNotExists_ShouldThrowProductNotFoundException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> {
            service.getProductById(1L);
        }, "Should throw ProductNotFoundException when product is not found.");

    }


    @Test
    public void testCreateProduct_WhenProductIsCreated_ShouldSaveProduct() {
        CreateProductRequest request = new CreateProductRequest("name", "description", 100.0, 10);
        Product product = new Product("name", "description", 100.0, 10);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = service.createProduct(request);

        assertEquals("name", createdProduct.getName());
        assertEquals("description", createdProduct.getDescription());
        assertEquals(100.0, createdProduct.getPrice());
        assertEquals(10, createdProduct.getStockQuantity());

    }


    @Test
    public void testIsProductInStock_WhenStockIsPositive_ShouldReturnTrue() {
        Product product = new Product(1L, "name", "description", 10.0, 100);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        boolean expectedResult = service.isProductInStock(1L);
        assertTrue(expectedResult, "Product should be in stock.");
    }

    @Test
    public void testIsProductInStock_WhenStockIsZero_ShouldReturnFalse() {
        Product product = new Product(1L, "name", "description", 10.0, 0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        boolean expectedResult = service.isProductInStock(1L);
        assertFalse(expectedResult, "Product should not be in stock.");

    }

    @Test
    public void testUpdateProduct_WhenProductIsUpdated_ShouldSaveProduct() {
        UpdateProductRequest request = new UpdateProductRequest("new name", " new description", 120.0, 100);
        Product product = new Product(1L, "old name", "old description", 100.0, 10);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Product updatedProduct = new Product(1L, "new name", "new description", 120.0, 100);
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        Product result = service.updateProduct(1L, request);

        assertEquals("new name", result.getName());
        assertEquals("new description", result.getDescription());
        assertEquals(120.0, result.getPrice());
        assertEquals(100, result.getStockQuantity());


    }


}