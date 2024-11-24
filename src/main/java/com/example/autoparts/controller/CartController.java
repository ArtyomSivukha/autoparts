package com.example.autoparts.controller;

import com.example.autoparts.service.CartService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<?> getAllItems(){
        return new ResponseEntity<>(cartService.getCartItemsFromCurrent(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCartItem(@RequestParam Long autoPartId) {
        return new ResponseEntity<>(cartService.addCartItem(autoPartId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{autoPartID}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long autoPartID) {
        return new ResponseEntity<>(cartService.deleteCartItem(autoPartID), HttpStatus.OK);
    }
}