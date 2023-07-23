package com.example.swagger.HackerRank;

import java.util.Scanner;

public class SumOfDivisor {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int num = 6;
        int j =0;
        for(int i = 1;i<=num;i++){
            if(num%i==0) {
                j = j + i;
            }
        }
        System.out.println(j);
    }
}
