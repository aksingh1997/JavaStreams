package com.example.JavaStreams.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee implements Comparable {
    int id;
    String name;
    int salary;

    public void increaseSalary(int amount) {
        this.salary += amount;
    }

    @Override
    public int compareTo(Object o) {
        Employee obj = (Employee) o;
        return this.name.compareTo(((Employee) o).name);
    }
}
