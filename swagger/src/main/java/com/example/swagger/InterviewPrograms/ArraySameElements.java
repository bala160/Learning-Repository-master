package com.example.swagger.InterviewPrograms;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ArraySameElements {
    public static void main(String[] args) {

        int[] arr1 = {1, 2, 3, 2, 1};
        int[] arr2 = {1, 2, 3};

        Set<int[]> s = new HashSet<>(Arrays.asList(arr1));
        Set<int[]> s1 = new HashSet<>(Arrays.asList(arr2));

        if (s1.size() != s.size()) {
            System.out.println("Size mismatch");
        }

        for (int[] i : s) {
            if (!s1.contains(i)) {
                System.out.println("not same array");
            }
        }
        System.out.print("SAME ARRAY");
    }
}
