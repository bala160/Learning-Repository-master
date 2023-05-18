package com.example.swagger.InterviewPrograms;

public class MaxCountElement {
    public static void main(String[] args) {
        int[] arr = {1, 4, 4, 3, 4};
        int max = 0;
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            int count = 0;
            for (int j = 0; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    //System.out.print(count);
                    count++;
                }
            }
            if (count > max) {
                max = count;
                res = arr[i];
            }
        }
        System.out.println(res +" "+"count is: "+max);
    }
}
