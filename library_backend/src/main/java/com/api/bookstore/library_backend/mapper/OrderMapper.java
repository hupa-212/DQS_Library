package com.api.bookstore.library_backend.mapper;


import org.springframework.stereotype.Component;

import com.api.bookstore.library_backend.dto.response.OrderDetailResponse;
import com.api.bookstore.library_backend.dto.response.OrderResponse;
import com.api.bookstore.library_backend.model.Order;
import com.api.bookstore.library_backend.model.OrderDetail;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .userName(order.getUser().getUsername())
                .orderDetails(order.getOrderDetails().stream()
                        .map(this::toOrderDetailResponse)
                        .collect(Collectors.toList()))
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus().name())
                .notes(order.getNotes())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    public OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .bookId(orderDetail.getBook().getId())
                .bookTitle(orderDetail.getBook().getTitle())
                .quantity(orderDetail.getQuantity())
                .price(orderDetail.getPrice())
                .totalPrice(orderDetail.getTotalPrice())
                .build();
    }
}