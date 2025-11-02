package com.api.bookstore.library_backend.model;

import java.time.Instant;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "books")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Book {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String author;

    @Column(nullable = false, unique = true)
    String isbn;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    Category category;

    @Column(nullable = false)
    Double price;

    @Column(columnDefinition = "TEXT")
    String description;

    String coverImageUrl;

    @Column(nullable = false)
    @Builder.Default
    Integer quantity = 0;

    @Column(nullable = false)
    @Builder.Default
    Integer quantitySold = 0;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<OrderDetail> orderDetails;

    @CreationTimestamp
    Instant createdAt;
    
    @UpdateTimestamp
    Instant updatedAt;

    @Column
    @Builder.Default
    Boolean isDeleted = false;
}