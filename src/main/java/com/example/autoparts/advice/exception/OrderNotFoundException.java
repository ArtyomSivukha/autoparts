package com.example.autoparts.advice.exception;

import lombok.Data;

public class OrderNotFoundException extends NotFoundException {

    public OrderNotFoundException(Long id) {
        super(id);
    }
}
