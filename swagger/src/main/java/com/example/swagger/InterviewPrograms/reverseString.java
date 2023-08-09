package com.example.swagger.InterviewPrograms;

public class reverseString {
    public static void main(String[] args){
        /*StringBuilder s = new StringBuilder("Bala");
        s.reverse();*/

        //System.out.print(s);
        String name = "Bala";
        String rev = "";
        /*for(int i = name.length()-1;i>=0;i--){
            System.out.println(name.charAt(i));
        }*/

       /*for(int i = 0;i<name.length();i++){
           //System.out.println(name.charAt(i)+rev);
       rev = name.charAt(i)+rev;
       }
       System.out.print(rev);*/

        for(int i = name.length()-1;i>=0;i--){
            System.out.print(name.charAt(i));
        }
    }
}
