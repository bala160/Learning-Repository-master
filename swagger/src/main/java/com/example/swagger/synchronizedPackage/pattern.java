package com.example.swagger.synchronizedPackage;

public class pattern {

    public static void main(String[] a) {

        int num = 10;

        for (int i = 0; i <= num; i++) {
            for (int j = i; j <= num - 1; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }

    }
}
