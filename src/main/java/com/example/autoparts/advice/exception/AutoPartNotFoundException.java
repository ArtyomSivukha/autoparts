package com.example.autoparts.advice.exception;

import lombok.Data;

@Data
public class AutoPartNotFoundException extends RuntimeException {
    private Long id;
    public AutoPartNotFoundException(Long id) {
        this.id = id;
    }
}
