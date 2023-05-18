package com.example.swagger.InterviewPrograms;

import java.util.HashMap;
import java.util.Map;

public class FirstUniqueChar {
    public static char firstUniqueChar(String s) {
        Map<Character, Integer> charCount = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
              charCount.put(c, charCount.getOrDefault(c,0)+1);//get each character and count occurence
            //System.out.println(charCount);
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            //System.out.println(charCount.get(c));
            /*if (charCount.get(c) != 1) {//first repeated char
                return c;
            }*/
            if (charCount.get(c) == 1) {//first Non-repeated char
                return c;
            }
        }

        // If no unique character is found, return '\0'
        return '\0';
    }

    public static void main(String[] args) {
        Character result = firstUniqueChar("KriKshnar");
        System.out.println(result);
    }
}
