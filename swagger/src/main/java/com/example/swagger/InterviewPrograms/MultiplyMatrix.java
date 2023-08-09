package com.example.swagger.InterviewPrograms;

public class MultiplyMatrix {
    public static void main(String[] args) {
        int[][] matrix1 = {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}};
        int[][] matrix2 = {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}};
        int[][] temp = new int[matrix1.length][matrix1[0].length];
        //System.out.println(matrix1.length+" "+matrix1[0].length); row : 2 , column : 5
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                temp[i][j] = matrix1[i][j] * matrix2[i][j];
            }
        }

        for (int[] res : temp) {
            for (int fin : res) {
                System.out.print(fin + " ");
            }
            System.out.println();
        }
    }
}
