package com.api.bookstore.library_backend.controller;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.api.bookstore.library_backend.dto.request.BookCreationRequest;
import com.api.bookstore.library_backend.dto.request.BookUpdateRequest;
import com.api.bookstore.library_backend.dto.response.ApiResponse;
import com.api.bookstore.library_backend.dto.response.BookResponse;
import com.api.bookstore.library_backend.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)   
public class BookController {
    BookService bookService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BookResponse>> createBook(@Valid @RequestBody BookCreationRequest request) {
        BookResponse bookResponse = bookService.createBook(request);
        ApiResponse<BookResponse> response = ApiResponse.<BookResponse>builder()
                .message("Book created successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(bookResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookResponse>>> getAllBooks() {
        List<BookResponse> books = bookService.getAllBooks();
        ApiResponse<List<BookResponse>> response = ApiResponse.<List<BookResponse>>builder()
                .message("Books retrieved successfully")
                .statusCode(HttpStatus.OK.value())
                .data(books)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(@PathVariable Long id) {
        BookResponse book = bookService.getBookById(id);
        ApiResponse<BookResponse> response = ApiResponse.<BookResponse>builder()
                .message("Book retrieved successfully")
                .statusCode(HttpStatus.OK.value())
                .data(book)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookUpdateRequest request) {
        BookResponse bookResponse = bookService.updateBook(id, request);
        ApiResponse<BookResponse> response = ApiResponse.<BookResponse>builder()
                .message("Book updated successfully (categoryId, price, quantity, description, coverImageUrl)")
                .statusCode(HttpStatus.OK.value())
                .data(bookResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Book deleted successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/low-stock/{threshold}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getLowStockBooks(@PathVariable Integer threshold) {
        List<BookResponse> books = bookService.getLowStockBooks(threshold);
        ApiResponse<List<BookResponse>> response = ApiResponse.<List<BookResponse>>builder()
                .message("Low stock books retrieved successfully")
                .statusCode(HttpStatus.OK.value())
                .data(books)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}