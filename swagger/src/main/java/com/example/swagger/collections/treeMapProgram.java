package com.example.swagger.collections;

import com.example.swagger.controller.model.movies;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class treeMapProgram {
    public static void main(String[] a) {

        movies m = new movies("Bala", "Billa", 1997, 5);
        movies m1 = new movies("Bala", "Billa", 1998, 5);
        movies m2 = new movies("Bala", "Billa", 1999, 5);
        movies m3 = new movies("Bala", "Billa", 1997, 5);

        TreeMap<Integer, movies> data = new TreeMap<>();
        data.put(2, m);
        //data.put(null, null);error throw because doesnt allow null key
        data.put(3, null);
        data.put(4, m3);

        for (Map.Entry res : data.entrySet()) {
            System.out.println(res.getKey() + " " + res.getValue());
        }
    }
}
