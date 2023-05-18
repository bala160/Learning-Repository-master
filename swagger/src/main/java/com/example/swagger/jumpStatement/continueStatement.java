package com.example.swagger.jumpStatement;

public class continueStatement {
    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            if (i % 2 == 0) {
                continue;
            }
            System.out.println(i);
        }
    }
}
