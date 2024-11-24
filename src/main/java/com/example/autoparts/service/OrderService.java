package com.example.autoparts.service;

import com.example.autoparts.advice.exception.OrderNotFoundException;
import com.example.autoparts.model.Order;
import com.example.autoparts.repository.OrderRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public List<Order> getAllCurrent(){
        return userService.getCurrent().getOrders();
    }

    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order orderDetails) {
        return orderRepository.findById(id).map(order -> {
            order.setOrderDate(orderDetails.getOrderDate());
            order.setStatus(orderDetails.getStatus());
            order.setPaymentMethod(orderDetails.getPaymentMethod());
            order.setDeliveryStatus(orderDetails.getDeliveryStatus());
            order.setCartItems(orderDetails.getCartItems());
            order.setNotifications(orderDetails.getNotifications());
            return orderRepository.save(order);
        }).orElseThrow(() -> new RuntimeException("Order not found"));
    }
}