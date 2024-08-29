package com.example.demo.store.exception;

public class StoreOwnerNonMatchedException extends RuntimeException{
    public StoreOwnerNonMatchedException(String message) {
        super(message);
    }
}
