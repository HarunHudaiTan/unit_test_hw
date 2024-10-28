package com.productManagement.demo.service;

import com.productManagement.demo.exception.CategoryNotFoundException;
import com.productManagement.demo.model.Category;
import com.productManagement.demo.repository.CategoryRepository;
import com.productManagement.demo.request.CreateCategoryRequest;
import com.productManagement.demo.request.UpdateCategoryRequest;
import org.junit.Before;

import org.junit.Test;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {
    private CategoryRepository categoryRepository;
    private CategoryService service;

    @Before
    public void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        service = new CategoryService(categoryRepository);
    }


    @Test
    public void testGetAllCategories_ShouldReturnListOfCategories() {
        Category category = new Category(1L, "name");
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        List<Category> categories = service.getAllCategories();

        assertEquals(1, categories.size());
        assertEquals(category, categories.get(0));
        assertEquals("name", categories.get(0).getName());

    }


    @Test
    public void testGetCategoryById_WhenCategoryIdExists_ShouldReturnCategory() {

        Category category = new Category(1L, "name");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category result = service.getCategoryById(1L);

        assertNotNull(result, "Category should not be null.");
        assertEquals(category, result, "Category ID should match.");

    }

    @Test
    public void testGetCategoryById_WhenCategoryIdDoesNotExists_ShouldThrowCategoryNotFoundException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> {
            service.getCategoryById(1L);
        }, "Should throw CategoryNotFoundException when product is not found.");

    }

    @Test
    public void testCreateCategory_WhenCategoryIsCreated_shouldSaveCategory() {
        CreateCategoryRequest request = new CreateCategoryRequest("name");
        Category category = new Category("name");

        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        Category createdCategory = service.createCategory(request);

        assertEquals("name", createdCategory.getName(), "Category name should match.");
    }

    @Test
    public void testUpdateCategory_WhenCategoryIsUpdated_shouldSaveCategory() {
        UpdateCategoryRequest request = new UpdateCategoryRequest("new name");
        Category category = new Category(1L, "old name");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        Category updatedCategory = new Category(1L, request.name());
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);
        Category result = service.updateCategory(1L, request);

        assertEquals(1L, result.getId(), "Category ID should match.");
        assertEquals("new name", result.getName(), "Category name should match.");


        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }


}
