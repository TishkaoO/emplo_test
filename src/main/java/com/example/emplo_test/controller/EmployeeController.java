package com.example.emplo_test.controller;

import com.example.emplo_test.dto.EmployeeDto;
import com.example.emplo_test.repo.EmployeeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeDao employeeDao;


    @GetMapping("employee/{id}")
    public EmployeeDto getEmployeeById(@PathVariable Long id) {
        return employeeDao.findById(id);
    }

    @PostMapping("save")
    public void getEmployeeById(@RequestBody EmployeeDto employee) {
       employeeDao.saveOrUpdateEmployee(employee);
    }
}
