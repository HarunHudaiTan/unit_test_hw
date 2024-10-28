package com.productManagement.demo.service;


import com.productManagement.demo.exception.CategoryNotFoundException;
import com.productManagement.demo.model.Category;
import com.productManagement.demo.model.Product;
import com.productManagement.demo.repository.CategoryRepository;
import com.productManagement.demo.request.CreateCategoryRequest;
import com.productManagement.demo.request.UpdateCategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return findCategoryById(id);
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new CategoryNotFoundException("Category not found by id"));
    }

    public Category createCategory(CreateCategoryRequest request) {
        Category category = new Category(request.name());
        return categoryRepository.save(category);


    }

    public Category updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = findCategoryById(id);
        Category updatedCategory = new Category(category.getId(), request.name());
        return categoryRepository.save(updatedCategory);
    }

    public void deleteCategory(Long id) {

        categoryRepository.deleteById(id);
    }


}
