package com.example.autoparts.advice.exception;

import lombok.Data;

@Data
public class UserNotFoundException extends RuntimeException {
    private Long id;
    public UserNotFoundException(Long id) {
        this.id = id;
    }
}
