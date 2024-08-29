package com.example.demo.exception;

public class StoreOwnerNonMatchedException extends RuntimeException{
    public StoreOwnerNonMatchedException(String message) {
        super(message);
    }
}
