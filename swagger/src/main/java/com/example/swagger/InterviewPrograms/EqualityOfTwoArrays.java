package com.example.swagger.InterviewPrograms;

public class EqualityOfTwoArrays {
    public static void meth(int[] arr1, int[] arr2) {
        Boolean status = true;

        if (arr1.length == arr2.length) {
            for (int i = 0; i < arr1.length; i++) {
                if (arr1[i] != arr2[i]) {
                    status = false;
                }
            }

        } else {
            status = true;
        }

        if (status) {
            System.out.println("Arrays are equal");
        } else {
            System.out.println("Arrays are not equal");
        }
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 5, 4};
        int[] arr2 = {1, 2, 3, 3, 4};
        meth(arr1, arr2);
    }
}
