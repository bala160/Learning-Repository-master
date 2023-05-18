package com.example.swagger.InterviewPrograms;

public class FindMissingNumInArray {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 5, 6};
        int n = arr.length+1;
        int sum = (n * (n + 1)) / 2;
        int arrSum = 0;

        for(int i=0;i<arr.length;i++){
            arrSum += arr[i];
        }

        int result = sum - arrSum;
        System.out.println(result);
    }
}
