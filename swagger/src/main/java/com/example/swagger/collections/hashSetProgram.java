package com.example.swagger.collections;

import java.util.HashSet;

public class hashSetProgram {
    public static void main(String[] a){

        /**
         *
         *		• HashSet stores the elements by using a mechanism called hashing.
         * 		• HashSet contains unique elements only.
         * 		• HashSet allows null value.
         * 		• HashSet class is non synchronized.
         * HashSet doesn't maintain the insertion order.
         *
         */
        HashSet<Integer> data = new HashSet<>();
        data.add(2);
        data.add(1);
        data.add(2);
        data.add(3);
        System.out.println(data);
        //statement using lambda
        /*data.forEach((n) ->
            System.out.println(n)
        );*/
    }
}
