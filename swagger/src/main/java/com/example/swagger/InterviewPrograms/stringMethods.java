package com.example.swagger.InterviewPrograms;

public class stringMethods {
    public static void main(String[] args) {
        String name = "Bala Krishnan";
        String removeSpace = name.replaceAll("\\s","");

        System.out.println(removeSpace);
        System.out.println(name.toUpperCase());
        System.out.println(name.toLowerCase());

        String spaceChar = name.replace("a","S");
        System.out.println(spaceChar);

    }
}
