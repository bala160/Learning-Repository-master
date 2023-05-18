package com.example.swagger.InterviewPrograms;

public class reverseColumnAndRow {

    public static void method(int[][] arr) {

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }

    }

    public static void main(String[] args) {
        int[][] arr = {{1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};

        System.out.println("Before Reverse column");
        method(arr);
        System.out.println();

        int rows = arr.length;
        int cols = arr.length;
        //System.out.println(cols);
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols / 2; y++) {
                int temp = arr[x][y];
                arr[x][y] = arr[x][cols - y - 1];
                arr[x][cols - y - 1] = temp;
            }
        }

        System.out.println("After Reverse column");
        method(arr);
        System.out.println();

        for (int x = 0; x < rows / 2; x++) {
            for (int y = 0; y < cols; y++) {
                int temp = arr[x][y];
                arr[x][y] = arr[rows - x - 1][y];
                arr[rows - x - 1][y] = temp;
            }
        }

        System.out.println("After Reverse Row");
        method(arr);
    }
}

