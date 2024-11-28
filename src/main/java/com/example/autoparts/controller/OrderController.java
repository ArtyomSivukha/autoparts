package com.example.autoparts.controller;

import com.example.autoparts.service.OrderService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getAllCurrent() {
        return new ResponseEntity<>(orderService.getAllCurrent(), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOneById(@PathVariable Long orderId) {
        return new ResponseEntity<>(orderService.getOneById(orderId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder() {
        return new ResponseEntity<>(orderService.createOrder(), HttpStatus.OK);
    }

}