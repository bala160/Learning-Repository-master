package com.example.swagger.CodeChefPrograms;

import java.util.Scanner;

public class Program1 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int[] arr = new int[5];
        arr[0]=sc.nextInt();
        arr[1]=sc.nextInt();
        arr[2]=sc.nextInt();
        arr[3]=sc.nextInt();
        arr[4]=sc.nextInt();

        int target = 10;
        int count = 0;
        for (int i = 0; i < arr.length; i++) {

            if (target < arr[i]) {
                count++;
            }
        }
        System.out.println(count);
    }
}
