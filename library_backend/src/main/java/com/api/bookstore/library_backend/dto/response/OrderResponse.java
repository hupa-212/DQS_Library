package com.api.bookstore.library_backend.dto.response;

import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private Long userId;
    private String userName;
    private List<OrderDetailResponse> orderDetails;
    private Double totalPrice;
    private String status;
    private String notes;
    private Instant createdAt;
    private Instant updatedAt;
}