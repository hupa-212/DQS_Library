package com.api.bookstore.library_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.api.bookstore.library_backend.dto.request.CategoryCreationRequest;
import com.api.bookstore.library_backend.dto.request.CategoryUpdateRequest;
import com.api.bookstore.library_backend.dto.response.CategoryResponse;
import com.api.bookstore.library_backend.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "books", ignore = true)
    Category toCategoryFromCreationRequest(CategoryCreationRequest categoryCreationRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "books", ignore = true)
    void updateCategoryFromUpdateRequest(
            @MappingTarget Category category, 
            CategoryUpdateRequest categoryUpdateRequest);

    CategoryResponse toCategoryResponse(Category category);
}