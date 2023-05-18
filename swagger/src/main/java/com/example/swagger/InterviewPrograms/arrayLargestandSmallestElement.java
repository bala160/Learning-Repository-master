package com.example.swagger.InterviewPrograms;

public class arrayLargestandSmallestElement {
    public static void main(String[] a) {
        int[] arr = {7, 1, 8, 4, 5};
        int max = arr[0];

        arrayLargestandSmallestElement obj = new arrayLargestandSmallestElement();
        obj.largest(arr, max);
        obj.smallest(arr, max);
    }

    void largest(int[] arr, int max) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) { // int[] arr = {7, 1, 8, 4, 5};
                max = arr[i];
            }
        }
        System.out.println("Largest number is " + " " + max);
    }

    void smallest(int[] arr, int min) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        System.out.println("Smallest number is " + " " + min);
    }
}
