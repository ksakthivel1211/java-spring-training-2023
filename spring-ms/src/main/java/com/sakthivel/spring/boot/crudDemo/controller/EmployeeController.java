package com.sakthivel.spring.boot.crudDemo.controller;

import com.sakthivel.spring.boot.crudDemo.requestModel.EmployeeRequest;
import com.sakthivel.spring.boot.crudDemo.resposeModel.ControllerResponse;
import com.sakthivel.spring.boot.crudDemo.resposeModel.EmployeeResponse;
import com.sakthivel.spring.boot.crudDemo.services.EmployeeService;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author sakthivel
 */
@RestController
@RequestMapping("/meeting")
public class EmployeeController {

    private EmployeeService employeeService;
    private EmployeeRequest employeeRequest;


    @Autowired
    public EmployeeController(EmployeeService theEmployeeService, EmployeeRequest theEmployeeRequest){
        employeeService = theEmployeeService;
        employeeRequest = theEmployeeRequest;
    }

    /**
     * findAllEmployee method gives details of all employees
     * @return - returns List of Employee objects
     */
    @GetMapping("/employee")
    public ResponseEntity<List<EmployeeResponse>> findAllEmployee()
    {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAllEmployees());
    }

    /**
     * getEmployee method gives the details of employee whose id is given
     * @param employeeId - employeeId of type int passed as parameter
     * @return - return employee object whose id is given
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable int employeeId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployeeById(employeeId));
    }

    /**
     * addEmployee methods saves the employee object to the DB
     * @param employeeRequestValue - Request body is passed as parameter of type EmployeeRequest
     * @return - returns String message for the confirmation of saving the object
     */
    @PostMapping("/employee/{teamId}")
    public ResponseEntity<ControllerResponse> addEmployee(@RequestBody EmployeeRequest employeeRequestValue)
    {

        return ResponseEntity.status(HttpStatus.OK).body(employeeService.addEmployee(employeeRequest.getEmployeeInstance(employeeRequestValue), employeeRequestValue.getTeamId());
    }

    /**
     * updateEmployee method updates the employee object to the DB
     * @param employeeRequestValue - Request body is passed as parameter of type EmployeeRequest
     * @return returns response object of type ControllerResponse
     */
    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<ControllerResponse> updateEmployee(@RequestBody EmployeeRequest employeeRequestValue)
    {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.updateEmployee(employeeRequest.getEmployeeInstance(employeeRequestValue), employeeRequestValue.getEmployeeId());
    }

    /**
     * deleteEmployee method deletes the employee
     * @param empId - employeeId of type int passed as parameter
     * @return returns response object of type ControllerResponse
     */
    @DeleteMapping("/employee/{empId}")
    public ResponseEntity<ControllerResponse> deleteEmployee(@PathVariable int empId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.deleteEmployee(empId));
    }

}
