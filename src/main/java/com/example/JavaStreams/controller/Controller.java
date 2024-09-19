package com.example.JavaStreams.controller;

import com.example.JavaStreams.model.Employee;
import com.example.JavaStreams.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
public class Controller {
    @Autowired
    EmployeeService employeeService;
    /* Note : Access all the apis at location - http://localhost:8080/swagger-ui/index.html*/

    @GetMapping("/hello")
    public String getHello() {
        return "Hello";
    }

    @PostMapping("streams/addEmployee")
    public String addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
        return "added " + employee.toString();
    }

    @PostMapping("streams/addManyEmployee")
    public String addManyEmployee(@RequestBody List<Employee> employees) {
        employeeService.addManyEmployee(employees);
        return "added " + employees.toString();
    }

    @GetMapping("/streams/employee/all")
    public ArrayList<Employee> getAllEmployees() {
        return  employeeService.getEmployeeList();
    }

    @PutMapping("streams/employee/incrementById/{id}/{increment}")
    public String incrementSalary(@PathVariable int id, @PathVariable int increment) {
        employeeService.incrementSalaryById(id, increment);
        return "Salary incremented!";
    }

    @PutMapping("streams/employee/incrementByName/{name}/{increment}")
    public String incrementSalary(@PathVariable String name, @PathVariable int increment) {
        employeeService.incrementSalaryByName(name, increment);
        return "Salary incremented!";
    }

    @GetMapping("streams/employee/stats")
    public String getStats() {
        return employeeService.getStats();
    }

    @GetMapping("/streams/employee/groupByFirstChar")
    public List<Employee> groupByFirstChar() {
        return employeeService.groupByFirstChar();
    }

    @GetMapping("/streams/employee/groupBySalary")
    public List<Employee> groupBySalary() {
        return employeeService.groupBySalary();
    }

    @GetMapping("optional")
    public void testOptional() {
        String[] objects = {"Abhi", null, "Hello", null};
        /* converting it into list of optional strings */
        List<Optional<String>> val = Stream.of(objects).map(Optional::ofNullable).toList();
        /* below line won't work as for Optional.of(param), param should be non-null, or it will throw exception */
        //List<Optional<String>> val = Stream.of(objects).map(Optional::of).toList();

        //printing length of only non-null strings, ignoring null values
        val.forEach(x -> x.ifPresent(y -> System.out.println(y.length())));

        Arrays.asList(objects).forEach(this::printValues);
    }

    public void printValues(String x) {
        String p = null;
        try {
            p = Optional.of(x).orElseThrow();
        } catch (Exception ex) {
            System.out.println("Null string");
        }
        System.out.print(p);

    }

}
