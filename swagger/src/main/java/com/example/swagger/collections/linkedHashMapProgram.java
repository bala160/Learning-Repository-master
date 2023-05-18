package com.example.swagger.collections;

import com.example.swagger.controller.model.movies;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class linkedHashMapProgram {
    public static void main(String[] a) {

        movies m = new movies("Bala", "Billa", 1997, 5);
        movies m1 = new movies("Bala", "Billa", 1998, 5);
        movies m2 = new movies("Bala", "Billa", 1999, 5);
        movies m3 = new movies("Bala", "Billa", 1997, 5);

        LinkedHashMap<Integer, movies> data = new LinkedHashMap<>();
        data.put(2, m);
        data.put(null, null);
        data.put(3, null);
        data.put(4, m3);

        for (Map.Entry res : data.entrySet()) {
            System.out.println(res.getKey() + " " + res.getValue());
        }
    }
}
