package com.example.swagger.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * 	Java LinkedList class uses a doubly linked list to store the elements
 *
 * 	- Java LinkedList class can contain duplicate elements.
 * 	- Java LinkedList class maintains insertion order.
 * 	- Java LinkedList class is non synchronized.
 * 	- In Java LinkedList class, manipulation is fast because no shifting needs to occur.
 * Java LinkedList class can be used as a list, stack or queue.
 */
public class linkedListProgram {
    public static void main(String[] args) {
        LinkedList<String> data = new LinkedList<>();
        data.add("Bala");
        // data.add("");
        data.add("Priya");

        //System.out.println(data.peekFirst());
        //Iterator<String> e = data.iterator();

       /* while (e.hasNext()) {
            System.out.println(e.next());
        }*/

        data.forEach(number -> System.out.println(number));

    }
}
