package com.example.autoparts.advice.exception;

import lombok.Data;

public class AutoPartNotFoundException extends NotFoundException {

    public AutoPartNotFoundException(Long id) {
        super(id);
    }
}
