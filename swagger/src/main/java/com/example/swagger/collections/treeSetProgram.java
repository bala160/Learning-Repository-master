package com.example.swagger.collections;

import java.util.HashSet;
import java.util.TreeSet;

public class treeSetProgram {

    public static void main(String[] a){

        /**
         *	- Java TreeSet class contains unique elements only like HashSet.
         * 	- Java TreeSet class access and retrieval times are quiet fast.
         * 	- Java TreeSet class doesn't allow null element.
         * 	- Java TreeSet class is non synchronized.
         * Java TreeSet class maintains ascending order.
         */

        TreeSet<Integer> data = new TreeSet<>();
        data.add(2);
        data.add(1);
        //data.add(null);//does not allow null element
        data.add(3);
        //statement using lambda
        data.forEach((n) ->
                System.out.println(n)
        );
    }

}
