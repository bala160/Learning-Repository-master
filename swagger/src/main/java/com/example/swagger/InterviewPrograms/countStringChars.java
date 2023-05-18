package com.example.swagger.InterviewPrograms;

public class countStringChars {
    public static void main(String[] a){

        String name = "Bala Krishnan";
        int count = 0;
        for(int i = 0;i<name.length();i++){
            if(name.charAt(i)!=' '){
                count++;
            }
        }
        System.out.println(count);
    }
}
