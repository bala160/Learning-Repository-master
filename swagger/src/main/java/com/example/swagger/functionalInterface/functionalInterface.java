package com.example.swagger.functionalInterface;

import com.example.swagger.controller.model.movies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class functionalInterface {

    public static void main(String[] args) {
        List<movies> data = Arrays.asList(new movies("Vijay", "Bigil", 2021, 4),
                new movies("Surya", "sp", 2022, 5),
                new movies("Surya", "sp", 2022, 5),
                new movies("Ajith", "mangatha", 2023, 2));


        method(data, d -> d.getActorame().equalsIgnoreCase("Surya"));
        method1(data, d -> d.getActorame().equals("Vijay"));
    }

    public static void method(List<movies> movie, Predicate<movies> m) {

        for (movies mo : movie) {
            if (m.test(mo)) {
                System.out.println(mo.releaseYear);
            }
        }
    }

    public static void method1(List<movies> movie, Function<movies, Boolean> m) {

        for (movies mo : movie) {
            if (m.apply(mo)) {
                System.out.println(mo.rating);
            }
        }
    }
}
