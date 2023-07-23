package com.example.swagger.HackerRank;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static void main(String[] args) {
        String REGEX = ".*www.*";

        // create the string
        // in which you want to search
        String actualString
                = "www.geeksforgeeks.org";

        // compile the regex to create pattern
        // using compile() method
        Pattern pattern = Pattern.compile(REGEX);

        // get a matcher object from pattern
        Matcher matcher = pattern.matcher(actualString);
        System.out.println(matcher);
        // check whether Regex string is
        // found in actualString or not
        boolean matches = matcher.matches();

        /*System.out.println("actualString "
                + "contains REGEX = "
                + matches);*/
    }
}
