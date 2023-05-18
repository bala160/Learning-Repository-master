package com.example.swagger.InterviewPrograms;

public class SumDigit {
    public static void main(String[] args) {
        int num = 564;
        int digit, sum = 0;

        while(num>0) {
            digit = num % 10;
            sum += digit;
            num /= 10;
        }
        System.out.println(sum);
    }
}
