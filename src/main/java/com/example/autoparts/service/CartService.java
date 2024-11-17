package com.example.autoparts.service;

import com.example.autoparts.model.Cart;
import com.example.autoparts.model.CartItem;
import com.example.autoparts.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Optional<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart addCartItem(Long cartId, CartItem cartItem) {
        return cartRepository.findById(cartId).map(cart -> {
            cartItem.setCart(cart); // Связываем CartItem с корзиной
            cart.getItems().add(cartItem);
            return cartRepository.save(cart);
        }).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public Cart removeCartItem(Long cartId, Long cartItemId) {
        return cartRepository.findById(cartId).map(cart -> {
            cart.getItems().removeIf(item -> item.getCartItemId().equals(cartItemId));
            return cartRepository.save(cart);
        }).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}