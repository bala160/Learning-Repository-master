package com.example.swagger.practise;

import java.util.Arrays;

public class stream {
    public static void main(String[] args) {
        int[] arr = {1,3,3,4,5};

        int dat = Arrays.stream(arr)
                .skip(1)
                .limit(1).sum();

        System.out.println(dat);
    }
}
