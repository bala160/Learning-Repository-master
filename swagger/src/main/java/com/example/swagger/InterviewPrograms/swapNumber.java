package com.example.swagger.InterviewPrograms;

public class swapNumber {
    public static void main(String[] args) {
        int a = 10, b = 5, c;

        c = a;
        a = b;
        b = c;

        System.out.println(a);
        System.out.println(b);

        //without using third variable
        a = a + b;
        b = a - b;
        a = a - b;

        System.out.println(a);
        System.out.println(b);
    }
}
