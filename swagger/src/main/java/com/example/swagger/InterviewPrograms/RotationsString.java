package com.example.swagger.InterviewPrograms;

import org.springframework.beans.factory.SmartInitializingSingleton;

public class RotationsString {

    public static boolean meth(String s1, String s2) {

        if (s1.length() != s2.length()) {
            return false;
        }
        String fin = s1 + s1;
        //System.out.println(fin); balabala
        return fin.contains(s2);
    }

    public static void main(String[] args) {
        String name1 = "bala";
        String name2 = "laba";

        boolean res = meth(name1, name2);

        if (res) {
            System.out.println("s1 and s2 are rotations of each other");
        } else {
            System.out.println("s1 and s2 are not rotations of each other");
        }


    }
}
