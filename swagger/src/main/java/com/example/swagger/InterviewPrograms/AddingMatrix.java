package com.example.swagger.InterviewPrograms;

import java.util.ArrayList;
import java.util.HashSet;

public class AddingMatrix {
    public static void main(String[] args) {
        int[][] num1 = {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}};
        int[][] num2 = {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}};
        int[][] temp = new int[num1.length][num1[0].length];

        for (int i = 0; i < num1.length; i++) {
            for (int j = 0; j < num1[0].length; j++) {
                temp[i][j] = num1[i][j] + num2[i][j]; //num1[0][0] = 1 + num2[0][0] = 1
                //System.out.println(temp[i][j]);
            }
        }
       /* int i = 0;
        while (i < num1.length) {
            int j = 0;
            while (j < num1[0].length) {
                temp[i][j] = num1[i][j] + num2[i][j];
                j++;
            }
            i++;
        }*/
        for (int[] res : temp) {
            for (int fin : res) {
                System.out.print(fin+" ");
            }
            System.out.println();
        }
    }
}
