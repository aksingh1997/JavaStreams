package com.example.JavaStreams.service;

import com.example.JavaStreams.model.Employee;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Getter
public class EmployeeService {
    private ArrayList<Employee> employeeList;

    EmployeeService() {
        this.employeeList = new ArrayList<Employee>();
    }

    public void addEmployee(Employee employee) {
        employeeList.add(employee);
    }

    public Optional<Employee> getEmployeeById(int id) {
        return employeeList.stream().filter(x -> x.getId() == id).findAny();
    }

    public void incrementSalaryById(int id, int increment) {
        employeeList.stream().filter(Objects::nonNull).filter(x -> x.getId() == id).forEach(x -> x.increaseSalary(increment));
    }

    public  List<Employee> getEmployeesByName(String name) {
        return  employeeList.stream().filter(Objects::nonNull).filter(x -> x.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    public void incrementSalaryByName(String name, int increment) {
        employeeList.stream().filter(Objects::nonNull).filter(x -> x.getName().equalsIgnoreCase(name))
                .forEach(x -> x.increaseSalary(increment));
    }

    public String getStats() {
        int maxSalary = getEmployeeList().stream().filter(Objects::nonNull).mapToInt(Employee::getSalary).max().orElseThrow(RuntimeException::new);
        int minSalary = getEmployeeList().stream().filter(Objects::nonNull).mapToInt(Employee::getSalary).min().orElseThrow(RuntimeException::new);
        double averageSalary = getEmployeeList().stream().filter(Objects::nonNull).mapToInt(Employee::getSalary).average().orElseThrow(RuntimeException::new);
        int sumSalary = getEmployeeList().stream().filter(Objects::nonNull).mapToInt(Employee::getSalary).sum();
        //learning reduce -- We can apply a function over each element of stream starting with initial value provided.
        // example - give me the sum of (salary / 2) for all the employees
        int sumSalaryReduced = getEmployeeList().stream().filter(Objects::nonNull).mapToInt(Employee::getSalary).reduce(0, (x, y) -> x + y / 2);

        String stats = "maxSalary:: " + maxSalary + " minSalary:: " + minSalary + " averageSalary:: " + averageSalary + " sumSalary:: " + sumSalary
                + " sumSalaryReduced:: " + sumSalaryReduced;

        System.out.println(stats);
        return stats;
    }

    public List<Employee> groupByFirstChar() {
        Map<Character, List<Employee>> employeeByChar = getEmployeeList().stream()
                .filter(Objects::nonNull).collect(Collectors.groupingBy(x -> x.getName().charAt(0)));

        Stream.of(employeeByChar).map(Map::entrySet).flatMap(Collection::stream).forEach(System.out::println);
        Stream.of(employeeByChar).forEach(System.out::println);
        // return employeeByChar.keySet().stream().map(employeeByChar::get).flatMap(Collection::stream).collect(Collectors.toList());
        return Stream.of(employeeByChar).map(Map::keySet).flatMap(Collection::stream).map(employeeByChar::get)
                .flatMap(Collection::stream).collect(Collectors.toList());


    }

    public List<Employee> groupBySalary() {
        Map<Boolean, List<Employee>> employeeBySalary = getEmployeeList().stream()
                .filter(Objects::nonNull).collect(Collectors.groupingBy(x -> x.getSalary() > 1000));

        Stream.of(employeeBySalary).map(Map::entrySet).flatMap(Collection::stream).forEach(System.out::println);

        return Stream.of(employeeBySalary).map(Map::keySet).flatMap(Collection::stream).map(employeeBySalary::get)
                .flatMap(Collection::stream).collect(Collectors.toList());
    }

    public void addManyEmployee(List<Employee> employee) {
        employeeList.addAll(employee);
    }
}
