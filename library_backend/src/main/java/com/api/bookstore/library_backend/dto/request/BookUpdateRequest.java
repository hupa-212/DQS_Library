package com.api.bookstore.library_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateRequest {
    
    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be positive")
    private Long categoryId;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    private Double price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @Size(max = 500, message = "Cover image URL must not exceed 500 characters")
    @Pattern(regexp = "^(https?://)?.*", message = "Cover image URL must be a valid URL")
    private String coverImageUrl;
}