package com.api.bookstore.library_backend.service;

import java.util.List;
import java.util.stream.Collectors;
import com.api.bookstore.library_backend.dto.request.BookCreationRequest;
import com.api.bookstore.library_backend.dto.response.BookResponse;
import com.api.bookstore.library_backend.mapper.BookMapper;
import com.api.bookstore.library_backend.model.Book;
import com.api.bookstore.library_backend.model.Category;
import com.api.bookstore.library_backend.repository.BookRepository;
import com.api.bookstore.library_backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class BookService {
    BookRepository bookRepository;
    CategoryRepository categoryRepository;
    BookMapper bookMapper;

    public BookResponse createBook(BookCreationRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));
        
        Book book = bookMapper.toBook(request, category);
        book.setIsDeleted(false);
        Book savedBook = bookRepository.save(book);
        log.info("Book created: {} with quantity: {}", savedBook.getTitle(), savedBook.getQuantity());
        return bookMapper.toBookResponse(savedBook);
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .filter(book -> !book.getIsDeleted())
                .map(bookMapper::toBookResponse)
                .collect(Collectors.toList());
    }

    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        if (book.getIsDeleted()) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        return bookMapper.toBookResponse(book);
    }

    public BookResponse updateBook(Long id, BookCreationRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        
        if (book.getIsDeleted()) {
            throw new RuntimeException("Book not found with id: " + id);
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setCategory(category);
        book.setPrice(request.getPrice());
        book.setQuantity(request.getQuantity());
        book.setDescription(request.getDescription());
        book.setCoverImageUrl(request.getCoverImageUrl());

        Book updatedBook = bookRepository.save(book);
        log.info("Book updated: {}", updatedBook.getTitle());
        return bookMapper.toBookResponse(updatedBook);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        book.setIsDeleted(true);
        bookRepository.save(book);
        log.info("Book deleted: {}", book.getTitle());
    }

    public void decrementBookQuantity(Long bookId, Integer quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        if (book.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient quantity for book: " + book.getTitle());
        }

        book.setQuantity(book.getQuantity() - quantity);
        book.setQuantitySold(book.getQuantitySold() + quantity);
        bookRepository.save(book);
        log.info("Book quantity decremented: {} - Remaining: {}", book.getTitle(), book.getQuantity());
    }

    public void incrementBookQuantity(Long bookId, Integer quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        book.setQuantity(book.getQuantity() + quantity);
        book.setQuantitySold(book.getQuantitySold() - quantity);
        bookRepository.save(book);
        log.info("Book quantity incremented: {} - New quantity: {}", book.getTitle(), book.getQuantity());
    }

    public List<BookResponse> getLowStockBooks(Integer threshold) {
        return bookRepository.findAll()
                .stream()
                .filter(book -> !book.getIsDeleted() && book.getQuantity() <= threshold)
                .map(bookMapper::toBookResponse)
                .collect(Collectors.toList());
    }
}