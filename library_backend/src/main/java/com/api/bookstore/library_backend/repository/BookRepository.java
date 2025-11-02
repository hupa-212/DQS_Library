package com.api.bookstore.library_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.bookstore.library_backend.model.Book;


public interface BookRepository extends JpaRepository<Book, Long> {

}
