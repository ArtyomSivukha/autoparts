package com.example.autoparts.advice.exception;


import lombok.Data;

@Data
public class NotFoundException extends RuntimeException{
    private Long id;

    public NotFoundException(Long id){
        this.id = id;
    }
}
