package com.example.swagger.HackerRank;

import java.util.Scanner;

public class ArchitectProgram {
    public static void main(String[] args) {
        int[] arr = new int[10];
        Scanner sc = new Scanner(System.in);

        for(int num = 0;num<10;num++){
            arr[num] = sc.nextInt();
        }
        //System.out.println(arr[arr.length-2]);
        int j;
        for (int i = 1; i < arr.length-2; i++) {
            for (j = i+1; j < arr.length-2; j++) {
                if (arr[i] == arr[j] && arr[i] == 0 && arr[j] == 0) {
                    System.out.println("subsequent number are same : " + arr[i] + " " + arr[j]);
                }
            }
        }
        if (arr[0] == 0) {
            System.out.println("First number is zero : " + arr[0]);
        }if (arr[arr.length-1] == 0) {
            System.out.println("last number is zero : " + " " + arr[arr.length-1]);
        }
    }
}
