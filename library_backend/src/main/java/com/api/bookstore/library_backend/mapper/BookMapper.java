package com.api.bookstore.library_backend.mapper;

import org.springframework.stereotype.Component;

import com.api.bookstore.library_backend.dto.request.BookCreationRequest;
import com.api.bookstore.library_backend.dto.response.BookResponse;
import com.api.bookstore.library_backend.dto.response.CategoryResponse;
import com.api.bookstore.library_backend.model.Book;
import com.api.bookstore.library_backend.model.Category;

@Component
public class BookMapper {
    
    public BookResponse toBookResponse(Book book) {
        CategoryResponse categoryResponse = CategoryResponse.builder()
                .id(book.getCategory().getId())
                .name(book.getCategory().getName())
                .description(book.getCategory().getDescription())
                .build();

        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .category(categoryResponse)
                .price(book.getPrice())
                .description(book.getDescription())
                .coverImageUrl(book.getCoverImageUrl())
                .quantity(book.getQuantity())
                .quantitySold(book.getQuantitySold())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }

    public Book toBook(BookCreationRequest request, Category category) {
        return Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .category(category)
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .quantitySold(0)
                .description(request.getDescription())
                .coverImageUrl(request.getCoverImageUrl())
                .build();
    }
}