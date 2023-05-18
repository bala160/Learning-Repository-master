package com.example.swagger.collections;

import java.util.LinkedList;
import java.util.Queue;

public class queue {
    public static void main(String[] args) {
        Queue<Integer> data = new LinkedList<>();
        data.add(3);
        data.add(12);
        data.add(1);
        System.out.println(data.peek());
        System.out.println(data);
        System.out.println(data.poll());
        System.out.println(data);
    }
}
