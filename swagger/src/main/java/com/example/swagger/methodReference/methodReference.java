package com.example.swagger.methodReference;

import com.example.swagger.entity.Employee;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class methodReference {

    void meth(){
        List<Integer> num1 = Arrays.asList(1,3,4,5,2);
    }
    public static void main(String[] args) {

        List<Integer> num = Arrays.asList(1,3,4,5,2);

        /*num.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(02);
            }
        });*/

        //num.sort((i1,i2) -> i1.compareTo(i2)); ----Lambda expression

        num.sort(Integer::compareTo);

        num.forEach(System.out::println);
    }
}
