package com.example.swagger;

import com.example.swagger.controller.model.movies;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

abstract class methodOverride {

    abstract void overridee();
}

public class sample extends methodOverride {
    public void overridee() {
        System.out.println("method override");
    }

    public void meth(int num1, int num2) {

        System.out.println("Method overload");
    }

    public void meth(int num1, String name) {

        System.out.println("Method overload");
    }

    public static void main(String[] args) {

        String[] words = {"What", "a", "are", "ae", "shortest", "word"};
        String temp = null;
        /*int num = 5;
        System.out.println(Math.sqrt(num));*/
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (words[i].length() > words[j].length()) {
                    temp = words[i];
                    words[i] = words[j];
                    words[j] = temp;
                }
            }
        }
        for (int i = 0; i < words.length; i++) {
            data.add(words[i]);
        }
        System.out.println(data.get(1));

       /* TreeMap<Integer, String> data = new TreeMap<>();
        for (String name : words) {
            data.put(name.length(), name);
        }
        //System.out.println(data);
        System.out.println(data.values().toArray()[1]);*/

    }
}
