package com.example.swagger.InterviewPrograms;

public class RemoveWhiteSpace {
    public static void main(String[] args) {
        String name = "B a l a";
        String res = name.replaceAll("\\s", "");
        System.out.println(res);
    }
}
