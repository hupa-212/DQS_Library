package com.api.bookstore.library_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.api.bookstore.library_backend.dto.request.CategoryCreationRequest;
import com.api.bookstore.library_backend.dto.request.CategoryUpdateRequest;
import com.api.bookstore.library_backend.dto.response.CategoryResponse;
import com.api.bookstore.library_backend.mapper.CategoryMapper;
import com.api.bookstore.library_backend.model.Category;
import com.api.bookstore.library_backend.repository.CategoryRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CategoryCreationRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category already exists with name: " + request.getName());
        }

        Category category = categoryMapper.toCategoryFromCreationRequest(request);
        category.setIsDeleted(false);
        
        Category savedCategory = categoryRepository.save(category);
        log.info("Category created: {}", savedCategory.getName());
        return categoryMapper.toCategoryResponse(savedCategory);
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        if (category.getIsDeleted()) {
            throw new RuntimeException("Category not found with id: " + id);
        }

        return categoryMapper.toCategoryResponse(category);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .filter(category -> !category.getIsDeleted())
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        if (category.getIsDeleted()) {
            throw new RuntimeException("Category not found with id: " + id);
        }

        if (!category.getName().equals(request.getName()) && 
            categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category already exists with name: " + request.getName());
        }

        categoryMapper.updateCategoryFromUpdateRequest(category, request);
        
        Category updatedCategory = categoryRepository.save(category);
        log.info("Category updated: {}", updatedCategory.getName());
        return categoryMapper.toCategoryResponse(updatedCategory);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        if (category.getIsDeleted()) {
            throw new RuntimeException("Category not found with id: " + id);
        }

        // ✅ Chỉ set isDeleted = true, KHÔNG thay đổi books
        category.setIsDeleted(true);
        categoryRepository.save(category);
        log.info("Category deleted (soft delete): {}", category.getName());
    }
}