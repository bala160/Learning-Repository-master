package com.example.swagger.pattern;

public class Program6 {

    public static void main(String[] a) {
        for (int row = 1; row <= 10; row++) {
            int totalColumn = row > 5 ? 10 - row : row;
            int space = 5 - totalColumn;
            //System.out.println(space);
            for (int s = 1; s <= space; s++) {
                System.out.print(" ");
            }
            for (int col = 1; col <= totalColumn; col++) {
                System.out.print(col+" ");
            }
            System.out.println();
        }
    }
}
