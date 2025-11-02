package com.api.bookstore.library_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreationRequest {
    
    @NotEmpty(message = "Order must contain at least one item")
    @Size(min = 1, max = 100, message = "Order must contain between 1 and 100 items")
    private List<OrderDetailRequest> orderDetails;

    @Size(max = 255, message = "Notes must not exceed 255 characters")
    private String notes;
}