package com.example.swagger.collect;

import ch.qos.logback.core.CoreConstants;
import com.example.swagger.controller.model.movies;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.maxBy;

public class streamCollectors {

    public static void main(String[] a) {

        List<movies> data = Arrays.asList(new movies("Vijay","Bigil",2021,4),
                new movies("Surya","sp",2022,5),
                new movies("Surya","sp",2022,5),
                new movies("Ajith","mangatha",2022,2));

        List<String> asList = data.stream().map(movies::getActorame).collect(Collectors.toList());
        //asList.forEach(System.out::println);

        Double d = data.stream()
               .collect(Collectors.averagingInt(movies::getRating));
        //System.out.println(d);

    TreeSet<Integer> res = data.stream()
            .map(movies::getReleaseYear)
            .collect(Collectors.toCollection(TreeSet::new));
    //res.forEach(System.out::println);
    }
}
