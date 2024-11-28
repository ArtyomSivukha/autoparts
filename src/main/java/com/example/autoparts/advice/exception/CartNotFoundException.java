package com.example.autoparts.advice.exception;

public class CartNotFoundException extends NotFoundException{
    public CartNotFoundException(Long id) {
        super(id);
    }
}
