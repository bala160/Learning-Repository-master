package com.example.swagger.stream;

import com.example.swagger.entity.Employee;

import java.util.*;
import java.util.stream.Collectors;

public class stream {

    public static void main(String[] args) {

        List<Employee> data = new ArrayList<>();
        data.add(new Employee("bala", "krishnan", 15000.00, List.of("Project 1", "Project 2")));
        data.add(new Employee("Nikhil", "Gupta", 6000.0, List.of("Project 1", "Project 3")));

        List<Double> res = data.stream()
                .map(Employee::getSalary) // fetch the value
                .filter(salary -> salary > 7000) //check the conditions
                .collect(Collectors.toList()); //return type

        System.out.println(res);

        Employee e = data
                .stream()
                .filter(salary -> salary.getSalary() > 7000)
                .map(employee -> new Employee(
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary()  * 1.10,
                        employee.getProjects()))
                .findFirst()
                .orElse(null);

        System.out.println(e);

        List<Employee> res1 = data.stream()
                .sorted((i1,i2) -> i1.getFirstName().compareTo(i2.getFirstName()))
                .collect(Collectors.toList());

        System.out.println(res1);

        Employee dat = data.stream()
                .max(Comparator.comparing(Employee::getSalary))
                .orElse(null);
        System.out.println(dat);

        Employee dat1 = data.stream()
                .min(Comparator.comparing(Employee::getSalary))
                .orElse(null);
        System.out.println(dat1);

    }
}
