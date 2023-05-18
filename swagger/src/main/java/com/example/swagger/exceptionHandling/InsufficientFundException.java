package com.example.swagger.exceptionHandling;

public class InsufficientFundException extends Throwable {
    int amount;
    public InsufficientFundException(int i) {
        amount = i;
    }

}
