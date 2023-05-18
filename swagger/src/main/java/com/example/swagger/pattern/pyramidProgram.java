package com.example.swagger.pattern;

public class pyramidProgram {
    public static void main(String[] args) {
        for (int i = 0; i <= 5; i++) {
            int totalColumn = i > 5 ? 5 - i : i;
            int space = 5 - totalColumn;
            for (int s = 1; s <= space; s++) {
                System.out.print(" ");
            }
            for (int j = 0; j <= totalColumn; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }
}
