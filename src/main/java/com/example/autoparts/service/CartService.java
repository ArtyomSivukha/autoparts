package com.example.autoparts.service;

import com.example.autoparts.controller.UserController;
import com.example.autoparts.model.AutoPart;
import com.example.autoparts.model.Cart;
import com.example.autoparts.model.CartItem;
import com.example.autoparts.repository.AutoPartRepository;
import com.example.autoparts.repository.CartRepository;
import com.example.autoparts.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final AutoPartRepository autoPartRepository;

    public List<CartItem> getCartItemsFromCurrent() {
        if (userService.getCurrent().getCart() == null) {
            Cart cart = new Cart();
            cart.setUser(userService.getCurrent());
            userService.getCurrent().setCart(cart);
            userRepository.save(userService.getCurrent());
        }
        return userService.getCurrent().getCart().getItems();
    }

    public Cart addCartItem(Long autoPartId) {
        CartItem cartItem = new CartItem();
        Cart cart = userService.getCurrent().getCart();
        AutoPart autoPart = autoPartRepository.findById(autoPartId).get();
        cartItem.setCart(cart);
        cartItem.setPart(autoPart);
        cartItem.setQuantity(1);
        cartItem.setPrice(autoPart.getPrice());
        cart.setTotalCost(cart.getTotalCost() + cartItem.getPrice());
        cart.getItems().add(cartItem);
        return cartRepository.save(cart);
    }

    public Cart deleteCartItem(Long cartItemId) {
        Cart cart = userService.getCurrent().getCart();
        CartItem itemToDelete = cart.getItems().stream()
                .filter(item -> item.getCartItemId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("В корзине отсутствует"));

        Float itemCost = itemToDelete.getPart().getPrice() * itemToDelete.getQuantity();
        cart.setTotalCost(cart.getTotalCost() - itemCost);

        cart.getItems().remove(itemToDelete);
        cartRepository.save(cart);
        return cartRepository.save(cart);
    }

    public Cart getCart() {
        return userService.getCurrent().getCart();
    }
}