package com.api.bookstore.library_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Integer quantity;
    private Double price;
    private Double totalPrice;
}