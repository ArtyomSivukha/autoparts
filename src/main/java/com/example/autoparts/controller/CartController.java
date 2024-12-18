package com.example.autoparts.controller;

import com.example.autoparts.service.CartService;
import jakarta.persistence.GeneratedValue;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping("/items")
    public ResponseEntity<?> getAllItems(){
        return new ResponseEntity<>(cartService.getCartItemsFromCurrent(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getCart(){
        return new ResponseEntity<>(cartService.getCart(), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addCartItem(@RequestParam Long cartItemId) {
        return new ResponseEntity<>(cartService.addCartItem(cartItemId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long cartItemId) {
        return new ResponseEntity<>(cartService.deleteCartItem(cartItemId), HttpStatus.OK);
    }
}