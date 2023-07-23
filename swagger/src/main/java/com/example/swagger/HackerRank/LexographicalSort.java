package com.example.swagger.HackerRank;

import java.util.Arrays;
import java.util.Scanner;

public class LexographicalSort {
    public static void main(String[] args) {
        /*String arr[] = new String[1];
        Scanner sc = new Scanner(System.in);
        arr[0] = sc.nextLine();
        *//*arr[1] = sc.nextLine();
        arr[2] = sc.nextLine();
        arr[3] = sc.nextLine();
        arr[4] = sc.nextLine();*//*
        //arr[5] = sc.nextLine();
        //arr[6] = sc.nextLine();

        Arrays.sort(arr,String.CASE_INSENSITIVE_ORDER);

        for(String name : arr){
            System.out.println(name);
            System.out.println(name.substring(name.length()-3,name.length()-0));
            System.out.println(name.substring(0,3));
        }*/

        /*Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        int num = sc.nextInt();*/

        String name = "welcometojava";
        int num = 3;
        System.out.println(getSmallestAndLargest(name,num));

    }

    public static String getSmallestAndLargest(String s, int k) {

        String smallest = "";
        String largest = "";

        StringBuffer sb = new StringBuffer();


        for(int i=0; i<s.length()-(k-1); i++){


            sb.append(s.substring(i,i+k)+" ");


        }


        String str = sb.toString();


        String arr[] = str.split(" ");


        java.util.Arrays.sort(arr);


        smallest = arr[0];


        largest = arr[arr.length-1];

        return smallest + "\n" + largest;
    }
}
