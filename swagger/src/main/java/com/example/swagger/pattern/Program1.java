package com.example.swagger.pattern;

public class Program1 {

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            System.out.print(" ");
            for (int j = 0; j <= i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }
}

