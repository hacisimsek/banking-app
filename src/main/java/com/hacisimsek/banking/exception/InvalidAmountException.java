package com.hacisimsek.banking.exception;

public class InvalidAmountException extends RuntimeException{
    public InvalidAmountException(String message, NumberFormatException e) {
        super(message);
    }
}
