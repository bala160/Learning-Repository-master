package com.example.swagger.InterviewPrograms;

import java.util.Arrays;
import java.util.HashMap;

public class CountString {
    public static void main(String[] args) {
        String name = "My Name Is Bala Is Is";
        String[] arr = name.split(" ");
        HashMap<String, Integer> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(arr[i])) {
                int count = map.get(arr[i]);
                //System.out.println(count);
                map.put(arr[i], count + 1);
            } else {
                map.put(arr[i], 1);
            }
        }
        //System.out.println(map);

        map.forEach((k,v) -> System.out.println(k+" "+v));
    }
}
