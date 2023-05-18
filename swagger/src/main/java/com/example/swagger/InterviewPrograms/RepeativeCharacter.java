package com.example.swagger.InterviewPrograms;

import java.util.HashMap;
import java.util.Map;

public class RepeativeCharacter {
    public static void main(String[] args) {
        String name= "geeksforgeeks";
        char[] c = name.toCharArray();
        Map<Character,Integer> res = new HashMap<>();
        for(char ca : c){
            if(res.containsKey(ca)){//check already character stored
                res.put(ca,res.get(ca)+1);
            }else{
                res.put(ca,1);
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
