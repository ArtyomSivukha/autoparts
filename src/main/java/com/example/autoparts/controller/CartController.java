package com.example.autoparts.controller;

import com.example.autoparts.model.Cart;
import com.example.autoparts.model.CartItem;
import com.example.autoparts.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        Optional<Cart> cart = cartService.getCartByUserId(userId);
        return cart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        return cartService.createCart(cart);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<Cart> addCartItem(@PathVariable Long cartId, @RequestBody CartItem cartItem) {
        try {
            Cart updatedCart = cartService.addCartItem(cartId, cartItem);
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<Cart> removeCartItem(@PathVariable Long cartId, @PathVariable Long cartItemId) {
        try {
            Cart updatedCart = cartService.removeCartItem(cartId, cartItemId);
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }
}