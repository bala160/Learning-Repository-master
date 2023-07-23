package com.example.swagger.HackerRank;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ExceptionHandling {
    public static void main(String[] args) {

        try {
            Scanner sc = new Scanner(System.in);
            int num1 = sc.nextInt();
            int num2 = sc.nextInt();
            int num3 = num1/num2;
            System.out.println(num3);
        }catch (InputMismatchException ob){
            System.out.println("java.util.InputMismatchException");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
