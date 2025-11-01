package com.api.bookstore.library_backend.dto.response;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private CategoryResponse category;
    private Double price;
    private String description;
    private String coverImageUrl;
    private Integer quantity;
    private Integer quantitySold;
    private Instant createdAt;
    private Instant updatedAt;
}