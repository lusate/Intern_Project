package com.omnilab.templatekotlin.exception;

public class NotEnoughStockException extends RuntimeException{

    public NotEnoughStockException(String message) {
        super(message);
    }

}
