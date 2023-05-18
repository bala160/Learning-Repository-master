package com.example.swagger.collections;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class linkedHashSetProgram {
    public static void main(String[] a){

        /**
         * 		• Java LinkedHashSet class contains unique elements only like HashSet.
         * 		• Java LinkedHashSet class is non-synchronized.
         * Java LinkedHashSet class maintains insertion order.
         */
        LinkedHashSet<Integer> data = new LinkedHashSet<>();
        data.add(1);
        data.add(1);
        data.add(null);
        data.add(3);
        //statement using lambda
        data.forEach((n) ->
                System.out.println(n)
        );
    }
}
