package com.example.swagger.InterviewPrograms;

import java.util.HashMap;
import java.util.Map;

public class RepeativeString {
    public static void main(String[] args) {
        String data = "Geek for Geek";
        Map<String,Integer> res = new HashMap<>();
        String[] str = data.split(" ");

        for(String fin : str){
            if(res.containsKey(fin)) {
                res.put(fin, res.get(fin) + 1);
            }else {
                res.put(fin,1);
            }
        }
        res.forEach((k,v) ->
        {
            if(v>1){
                System.out.println(k+" "+v);
            }
        });

    }
}
