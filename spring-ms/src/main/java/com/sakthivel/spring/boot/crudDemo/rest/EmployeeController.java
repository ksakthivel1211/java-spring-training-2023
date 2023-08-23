package com.sakthivel.spring.boot.crudDemo.rest;

import com.sakthivel.spring.boot.crudDemo.entity.Employee;
import com.sakthivel.spring.boot.crudDemo.respose.EmployeeResponse;
import com.sakthivel.spring.boot.crudDemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/meeting")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService theEmployeeService){
        employeeService = theEmployeeService;
    }

    @GetMapping("/employee")
    public List<EmployeeResponse> findAll()
    {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeResponse getEmployee(@PathVariable int employeeId)
    {
//        String employee = employeeService.getEmployeeById(employeeId).toString();
        return employeeService.getEmployeeById(employeeId);
    }

    @PostMapping("/employee/{teamId}")
    public String addEmployee(@RequestBody Employee theEmployee,@PathVariable int teamId)
    {
        return employeeService.addEmployee(theEmployee,teamId);
    }

    @PutMapping("/employee/{employeeId}")
    public String updateEmployee(@RequestBody Employee theEmployee, @PathVariable int employeeId)
    {
        return employeeService.updateEmployee(theEmployee,employeeId);
    }

    @DeleteMapping("/employee/{empId}")
    public String deleteEmployee(@PathVariable int empId)
    {
        return employeeService.deleteEmployee(empId);
    }

}
