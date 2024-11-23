package com.example.autoparts.advice;

import com.example.autoparts.advice.exception.AutoPartNotFoundException;
import com.example.autoparts.advice.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        return new ResponseEntity<>("Null pointer exception occurred: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleNullPointerException(UserNotFoundException ex) {
        return new ResponseEntity<>("Пользовтель не найден с id: " + ex.getId(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AutoPartNotFoundException.class)
    public ResponseEntity<?> handleNullPointerException(AutoPartNotFoundException ex) {
        return new ResponseEntity<>("Деталь не найдена с id: " + ex.getId(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}