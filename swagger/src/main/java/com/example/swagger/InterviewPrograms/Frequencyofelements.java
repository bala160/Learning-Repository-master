package com.example.swagger.InterviewPrograms;

import java.util.HashMap;
import java.util.Map;

public class Frequencyofelements {
    public static void main(String[] args) {
        int[] num = {10,20,10,30,30,40,30};

        Map<Integer,Integer> data = new HashMap<>();

        for(int i = 0;i<num.length;i++){
            if(data.containsKey(num[i])){
                int count = data.get(num[i]);
                data.put(num[i], count+1);
            }else {
                data.put(num[i], 1);
            }

        }
        data.forEach((k,v) -> System.out.println(k+" "+v));
    }
}
