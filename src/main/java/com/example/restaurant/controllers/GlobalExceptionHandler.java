package com.example.restaurant.controllers;

import com.example.restaurant.dtos.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageDTO handleRuntimeException(RuntimeException ex) {
        return new MessageDTO(ex.getMessage());
    }
}