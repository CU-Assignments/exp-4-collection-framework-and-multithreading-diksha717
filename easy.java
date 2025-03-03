package com.capgemini;

import java.util.*;

abstract class Person {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public abstract double calculateSalary();
}

class Employee extends Person {
    private double basic, allowance, da, ta;

    public Employee(String name, int age, double basic, double allowance, double da, double ta) {
        super(name, age);
        this.basic = basic;
        this.allowance = allowance;
        this.da = da;
        this.ta = ta;
    }

    @Override
    public double calculateSalary() {
        return basic + allowance + da - ta;
    }

    @Override
    public String toString() {
        return name + " - Age: " + age + ", Salary: $" + calculateSalary();
    }
}

public class EmployeeSorter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Employee> employees = new ArrayList<>();

        System.out.print("Enter number of employees: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("\nEnter Name: ");
            String name = sc.next();
            System.out.print("Enter Age: ");
            int age = sc.nextInt();
            System.out.print("Enter Basic Salary: ");
            double basic = sc.nextDouble();
            System.out.print("Enter Allowance: ");
            double allowance = sc.nextDouble();
            System.out.print("Enter DA: ");
            double da = sc.nextDouble();
            System.out.print("Enter TA: ");
            double ta = sc.nextDouble();

            employees.add(new Employee(name, age, basic, allowance, da, ta));
        }

        // Sort by salary (Descending order)
        employees.sort((e1, e2) -> Double.compare(e2.calculateSalary(), e1.calculateSalary()));

        System.out.println("\nSorted Employees (Highest to Lowest Salary):");
        employees.forEach(System.out::println);

        sc.close();
    }
}
