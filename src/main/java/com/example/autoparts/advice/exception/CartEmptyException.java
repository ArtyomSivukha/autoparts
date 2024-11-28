package com.example.autoparts.advice.exception;

public class CartEmptyException extends RuntimeException{
    public CartEmptyException(String message) {
        super(message);
    }
}
