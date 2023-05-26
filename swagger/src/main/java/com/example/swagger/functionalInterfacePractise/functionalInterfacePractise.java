package com.example.swagger.functionalInterfacePractise;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class functionalInterfacePractise {

    public static void main(String[] args) {
        movies m = new movies("Nayagan", "KamalHassan", 1997l);
        movies m1 = new movies("Ayan", "Surya", 2010l);
        movies m2 = new movies("Chandramuki", "Rajini", 2005l);

        List<movies> data = new ArrayList<>();
        data.add(m);
        data.add(m1);
        data.add(m2);
        test(data, lambda -> lambda.getReleaseYear() == 2010);
        test(data, lambda1 -> lambda1.getActorName().equalsIgnoreCase("Rajini"));

    }

    static void test(List<movies> m, Predicate<movies> moviesPredicate) {
        for (movies data : m) {
            if (moviesPredicate.test(data)) {
                System.out.println(data.getActorName());
            }
        }
    }
}
