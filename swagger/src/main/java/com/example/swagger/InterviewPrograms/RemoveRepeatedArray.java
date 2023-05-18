package com.example.swagger.InterviewPrograms;

import java.util.HashSet;
import java.util.Set;

public class RemoveRepeatedArray {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 1, 2};
        Set<Integer> data = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            data.add(arr[i]);
        }
        System.out.println(data);


        /*String name = "bala";
        char[] c = name.toCharArray();
        for (int i = 0; i < c.length; i++) {
            data.add(c[i]);
        }
        System.out.println(data);*/
    }
}
