package com.example.swagger.InterviewPrograms;

public class arrayAscendingandDescending {
    public static void main(String[] a) {
        int[] arr = {2, 1, 3, 4, 5};
        int temp = 0;

        arrayAscendingandDescending obj = new arrayAscendingandDescending();
        obj.ascending(arr, temp);
        obj.descending(arr, temp);
    }

    void ascending(int[] arr, int temp) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        System.out.println("Ascending Order");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
    }

    void descending(int[] arr, int temp) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] < arr[j]) { //int[] arr = {2, 1, 3, 4, 5};
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        System.out.println("Descending Order");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
    }
}
