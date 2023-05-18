package com.example.swagger.Lambda;

import com.example.swagger.controller.model.movies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class lambdaExpression {

    public static void main(String[] args) {


        List<String> data = new ArrayList<>();
        data.add("bala");
        data.add("priya");

       // data.forEach((name) -> System.out.println(name));


        List<Integer> res = new ArrayList<>();
        res.add(1);
        res.add(2);
        res.add(3);
        res.add(4);
        res.add(5);
        res.forEach((n) -> {
           // if (n % 2 == 0) System.out.println(n);
        });

        movies m = new movies("Bala","Billa",1997,5);
        movies m1 = new movies("Bala","Billa",1998,5);
        movies m2 = new movies("Bala","Billa",1999,5);
        movies m3 = new movies("Bala","Billa",1997,5);

        List<movies> names = new ArrayList<>();
        names.add(m);
        names.add(m1);
        names.add(m2);
        names.add(m3);

        names.forEach((n) ->{
            if(n.releaseYear>1997) System.out.println(n.releaseYear);
        });
    }
}
