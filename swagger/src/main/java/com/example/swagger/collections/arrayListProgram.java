package com.example.swagger.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Java ArrayList class uses a dynamic array for storing the elements
 * <p>
 * - Java ArrayList class can contain duplicate elements.
 * - Java ArrayList class maintains insertion order.
 * - Java ArrayList class is non synchronized.
 */
public class arrayListProgram {
    public static void main(String[] args) {

        String[] arr = {"Bala", "Priya"};
        List<String> data1 = Arrays.asList(arr);

        ArrayList<String> data = new ArrayList<>();
        data.addAll(data1);
        data.add("Bala");
        data.add("");
        data.add("Priya");

        Iterator<String> e = data.iterator();

        //while(e.hasNext()){
        //System.out.println(e.next());
        //}

        data.forEach((n) -> System.out.println(n));

    }
}
