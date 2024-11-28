package com.example.autoparts.service;

import com.example.autoparts.advice.exception.CartEmptyException;
import com.example.autoparts.advice.exception.CartNotFoundException;
import com.example.autoparts.advice.exception.OrderNotFoundException;
import com.example.autoparts.model.Cart;
import com.example.autoparts.model.CartItem;
import com.example.autoparts.model.Order;
import com.example.autoparts.model.User;
import com.example.autoparts.model.enums.DeliveryStatus;
import com.example.autoparts.model.enums.OrderStatus;
import com.example.autoparts.repository.CartRepository;
import com.example.autoparts.repository.OrderRepository;
import com.example.autoparts.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public List<Order> getAllCurrent() {
        return userService.getCurrent().getOrders();
    }

    public Order getOneById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Transactional
    public Order createOrder() {
        // Получение текущего пользователя
        User currentUser = userService.getCurrent();

        // Получение корзины пользователя
        Cart cart = currentUser.getCart();

        // Проверка, что корзина не пуста
        if (cart.getItems().isEmpty()) {
            throw new CartEmptyException("Корзина пуста, невозможно создать заказ.");
        }

        // Создание нового заказа
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(currentUser);
        order.setStatus(OrderStatus.PENDING); // Устанавливаем статус "новый"
        order.setDeliveryStatus(DeliveryStatus.PENDING); // Указанный статус доставки

        // Рассчитываем общую стоимость заказа
        order.setTotalCost(cart.getTotalCost());

        // Создаем новую коллекцию для cartItems
        List<CartItem> orderItems = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            item.setOrder(order); // Связываем CartItem с заказом
            orderItems.add(item); // Добавляем в новую коллекцию
        }
        order.setCartItems(orderItems); // Устанавливаем новую коллекцию в заказе

        // Сохранение заказа
        Order dbOrder = orderRepository.save(order);

        // Очистка корзины
        cart.getItems().clear();
        cartRepository.save(cart);

        return dbOrder;
    }

}