package com.example.swagger.InterviewPrograms;

public class reverseIntegerProgram {

    public static void main(String[] args){

        int num = 12345;
        String reverse = String.valueOf(num);
        char[] arr = reverse.toCharArray();
        for(int i = arr.length-1;i>=0;i--){
            System.out.println(arr[i]);
        }
       /* StringBuilder sb = new StringBuilder(reverse).reverse();
        int res = Integer.parseInt(String.valueOf(sb));
        System.out.println(res);*/
    }
}
