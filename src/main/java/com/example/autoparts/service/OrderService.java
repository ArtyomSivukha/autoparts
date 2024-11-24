package com.example.autoparts.service;

import com.example.autoparts.advice.exception.OrderNotFoundException;
import com.example.autoparts.model.Cart;
import com.example.autoparts.model.Order;
import com.example.autoparts.model.enums.OrderStatus;
import com.example.autoparts.repository.OrderRepository;
import com.example.autoparts.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public List<Order> getAllCurrent(){
        return userService.getCurrent().getOrders();
    }

    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order createOrder(Cart cart) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setUser(userService.getCurrent());
        order.setCartItems(cart.getItems());
        order.setTotalCost(cart.getTotalCost());
        userService.getCurrent().getOrders().add(order);
        return userRepository.save(userService.getCurrent()).getOrders().getFirst();
    }

}