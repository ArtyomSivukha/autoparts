package com.example.autoparts.advice.exception;

import lombok.Data;

@Data
public class OrderNotFoundException extends RuntimeException {
    private Long id;
    public OrderNotFoundException(Long id) {
        this.id = id;
    }
}
