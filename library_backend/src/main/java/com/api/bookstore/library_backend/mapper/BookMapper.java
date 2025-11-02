package com.api.bookstore.library_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.api.bookstore.library_backend.dto.request.BookCreationRequest;
import com.api.bookstore.library_backend.dto.request.BookUpdateRequest;
import com.api.bookstore.library_backend.dto.response.BookResponse;
import com.api.bookstore.library_backend.model.Book;
import com.api.bookstore.library_backend.model.Category;

@Mapper(componentModel = "spring")
public interface BookMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "quantitySold", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    @Mapping(target = "category", source = "category")
    @Mapping(target = "description", source = "bookCreationRequest.description")
    Book toBook(BookCreationRequest bookCreationRequest, Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "isbn", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "quantitySold", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    @Mapping(target = "description", source = "bookUpdateRequest.description")
    void updateBookFromUpdateRequest(
            @MappingTarget Book book,
            BookUpdateRequest bookUpdateRequest,
            Category category);

    BookResponse toBookResponse(Book book);
}