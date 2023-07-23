package com.example.swagger.HackerRank;

import java.util.Scanner;

public class IfElseProgram {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();

        if(num%2!=0){
            System.out.println("Weird");
        }else if(num%2==0 && num>2 && num<=5){
            System.out.println("Not weird");
        }else if(num%2==0 && num>6 && num<=20){
            System.out.println("weird");
        }else if(num%2==0 && num>20){
            System.out.println("Not weird");
        }
    }
}
