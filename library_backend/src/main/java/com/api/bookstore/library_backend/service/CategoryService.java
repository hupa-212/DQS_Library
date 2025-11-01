package com.api.bookstore.library_backend.service;

import java.util.List;
import java.util.stream.Collectors;
import com.api.bookstore.library_backend.dto.response.CategoryResponse;
import com.api.bookstore.library_backend.model.Category;
import com.api.bookstore.library_backend.repository.CategoryRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;

    public CategoryResponse createCategory(String name, String description) {
        Category category = Category.builder()
                .name(name)
                .description(description)
                .isDeleted(false)
                .build();
        Category savedCategory = categoryRepository.save(category);
        return toResponse(savedCategory);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .filter(c -> !c.getIsDeleted())
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        if (category.getIsDeleted()) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        return toResponse(category);
    }

    private CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}