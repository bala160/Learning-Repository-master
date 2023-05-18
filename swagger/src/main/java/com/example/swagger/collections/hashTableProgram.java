package com.example.swagger.collections;

import com.example.swagger.controller.model.movies;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class hashTableProgram {

    public static void main(String[] a){
        movies m = new movies("Bala", "Billa", 1997, 5);
        movies m1 = new movies("Bala", "Billa", 1998, 5);
        movies m2 = new movies("Bala", "Billa", 1999, 5);
        movies m3 = new movies("Bala", "Billa", 1997, 5);

        Hashtable<Integer, movies> data = new Hashtable<>();
        data.put(4, m);
        data.put(1, m1);
        data.put(2, m2);
        //data.put(3, null);
        // doesnt allow null key and value

        for(Map.Entry res : data.entrySet()){
            System.out.println(res.getKey()+" " +res.getValue());
        }
    }
}
