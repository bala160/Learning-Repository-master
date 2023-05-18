package com.example.swagger.InterviewPrograms;

import java.util.HashSet;
import java.util.Set;

public class removeDuplicateArray {
    public static void main(String[] args) {

        int[] arr = {1, 2, 3, 4, 2, 3, 41, 1, 5};
        removeDuplicateArray r = new removeDuplicateArray();
        r.method(arr);
    }

    void method(int[] arr) {

        Set<Integer> s = new HashSet<>();

        for (int i = 0; i < arr.length; i++) {
            s.add(arr[i]);
        }

        System.out.print(s);
    }
}
