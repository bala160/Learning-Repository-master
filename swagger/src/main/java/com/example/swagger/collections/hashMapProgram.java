package com.example.swagger.collections;

import com.example.swagger.controller.model.movies;

import java.util.HashMap;
import java.util.Map;

public class hashMapProgram {

    public static void main(String[] a) {

        movies m = new movies("Bala", "Billa", 1997, 5);
        movies m1 = new movies("Bala", "Billa", 1998, 5);
        movies m2 = new movies("Bala", "Billa", 1999, 5);
        movies m3 = new movies("Bala", "Billa", 1997, 5);

        HashMap<Integer, movies> data = new HashMap<>();
        // data.put(null, m);
        data.put(1, m1);
        data.put(2, m2);
        //data.put(null, m3);// doesnt print because one null key only accepted

        /*for(Map.Entry res : data.entrySet()){
            System.out.println(res.getKey()+" " +res.getValue());
        }*/

        data.forEach((n, t) -> System.out.println(n + " " + t));

        data.forEach((n, t) -> {
                    if (n == 2)
                        System.out.println(t.getReleaseYear());
                }
        );
    }
}
