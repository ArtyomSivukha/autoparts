package com.example.autoparts.advice.exception;

import lombok.Data;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(Long id) {
        super(id);
    }
}
