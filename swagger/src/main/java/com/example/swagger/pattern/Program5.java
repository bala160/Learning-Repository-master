package com.example.swagger.pattern;

public class Program5 {

    public static void main(String[] args) {

        for (int row = 1; row <= 10; row++) {
            //System.out.println(" ");
            int totalColumn = row > 5 ? 10 - row : row;
            for (int col = 1; col <= totalColumn; col++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }

}
