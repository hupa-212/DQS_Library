package com.api.bookstore.library_backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.api.bookstore.library_backend.dto.request.OrderCreationRequest;
import com.api.bookstore.library_backend.dto.request.OrderDetailRequest;
import com.api.bookstore.library_backend.dto.response.OrderResponse;
import com.api.bookstore.library_backend.mapper.OrderMapper;
import com.api.bookstore.library_backend.model.Book;
import com.api.bookstore.library_backend.model.Order;
import com.api.bookstore.library_backend.model.OrderDetail;
import com.api.bookstore.library_backend.model.User;
import com.api.bookstore.library_backend.repository.BookRepository;
import com.api.bookstore.library_backend.repository.OrderRepository;
import com.api.bookstore.library_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookService bookService;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponse createOrder(Long userId, OrderCreationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Order order = Order.builder()
                .user(user)
                .status(Order.OrderStatus.PENDING)
                .notes(request.getNotes())
                .isDeleted(false)
                .build();

        Double totalPrice = 0.0;
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderDetailRequest detail : request.getOrderDetails()) {
            Book book = bookRepository.findById(detail.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found with id: " + detail.getBookId()));

            // Kiểm tra số lượng có đủ không
            if (book.getQuantity() < detail.getQuantity()) {
                throw new RuntimeException("Insufficient quantity for book: " + book.getTitle() 
                    + ". Available: " + book.getQuantity() + ", Requested: " + detail.getQuantity());
            }

            // Giảm số lượng sách
            bookService.decrementBookQuantity(book.getId(), detail.getQuantity());

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .book(book)
                    .quantity(detail.getQuantity())
                    .price(book.getPrice())
                    .totalPrice(book.getPrice() * detail.getQuantity())
                    .isDeleted(false)
                    .build();

            orderDetails.add(orderDetail);
            totalPrice += orderDetail.getTotalPrice();
        }

        order.setOrderDetails(orderDetails);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);
        log.info("Order created: ID={}, User={}, Total={}", savedOrder.getId(), user.getUsername(), totalPrice);
        
        return orderMapper.toOrderResponse(savedOrder);
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        if (order.getIsDeleted()) {
            throw new RuntimeException("Order not found with id: " + id);
        }

        return orderMapper.toOrderResponse(order);
    }

    public List<OrderResponse> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .filter(order -> !order.getIsDeleted())
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .filter(order -> !order.getIsDeleted())
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getOrdersByStatus(String status) {
        try {
            Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status.toUpperCase());
            return orderRepository.findByStatus(orderStatus)
                    .stream()
                    .filter(order -> !order.getIsDeleted())
                    .map(orderMapper::toOrderResponse)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid order status: " + status);
        }
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        if (order.getIsDeleted()) {
            throw new RuntimeException("Order not found with id: " + id);
        }

        try {
            Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status.toUpperCase());
            order.setStatus(orderStatus);
            Order updatedOrder = orderRepository.save(order);
            log.info("Order status updated: ID={}, Status={}", id, status);
            return orderMapper.toOrderResponse(updatedOrder);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid order status: " + status);
        }
    }

    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        if (order.getIsDeleted()) {
            throw new RuntimeException("Order not found with id: " + id);
        }

        if (order.getStatus() == Order.OrderStatus.DELIVERED || order.getStatus() == Order.OrderStatus.CANCELLED) {
            throw new RuntimeException("Cannot cancel order with status: " + order.getStatus());
        }

        // Hoàn lại số lượng sách
        for (OrderDetail detail : order.getOrderDetails()) {
            bookService.incrementBookQuantity(detail.getBook().getId(), detail.getQuantity());
        }

        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
        log.info("Order cancelled: ID={}", id);
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        order.setIsDeleted(true);
        orderRepository.save(order);
        log.info("Order deleted: ID={}", id);
    }
}