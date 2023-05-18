package com.example.swagger.pattern;

public class Program7 {
    public static void main(String[] a) {
        int n = 5;
       /* for (int row = 1; row <= 2 * n; row++) {
            int c = row > n ? 2 * n - row : row;
            for (int space = 0; space < n - c; space++) {
                System.out.print(" ");
            }
            for (int col = c; col >= 1; col--) {
                System.out.print(col + " ");
            }
            for (int col = 2; col <= c; col++) {
                System.out.print(col + " ");
            }
            System.out.println();
        }*/

        for (int row = 1; row <= 2 * n; row++) {

            int c = row > n ? 2 * n - row: row;

            for (int space = 0; space < n-c; space++) {
                System.out.print("  ");
            }

            for (int col = c; col >= 1; col--) {
                System.out.print(col + " ");
            }
            for (int col = 2; col <= c; col++) {
                System.out.print(col + " ");
            }

            System.out.println();
        }
    }
}
