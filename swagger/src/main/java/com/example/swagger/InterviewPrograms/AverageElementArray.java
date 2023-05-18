package com.example.swagger.InterviewPrograms;

public class AverageElementArray {
    public static void main(String[] args) {
        int[] arr = {1,2,3,6,4,5};

        double sum = 0.0;
        double avg = 0.0;

        for(int i = 0;i<arr.length;i++){
            sum += arr[i];
        }

        avg = sum/arr.length;
        System.out.println(avg);
    }
}
