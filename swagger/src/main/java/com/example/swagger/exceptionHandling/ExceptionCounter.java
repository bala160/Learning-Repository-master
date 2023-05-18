package com.example.swagger.exceptionHandling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExceptionCounter {
    public static void main(String[] args) {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        Pattern pattern = Pattern.compile("throw throws try catch");

        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Balakrishnan\\Documents\\e.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String name = matcher.group();
                    if (counts.containsKey(name)) {
                        counts.put(name, counts.get(name) + 1);
                    } else {
                        counts.put(name, 1);
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String name : counts.keySet()) {
            int count = counts.get(name);
            if (count > 1) {
                System.out.println(name + ": " + count);
            }
        }
    }
}
