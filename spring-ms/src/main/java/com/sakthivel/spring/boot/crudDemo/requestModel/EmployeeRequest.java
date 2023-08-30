package com.sakthivel.spring.boot.crudDemo.requestModel;

import com.sakthivel.spring.boot.crudDemo.entity.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeRequest {

    private String firstname;
    private String lastName;
    private String email;
    private int teamId;
    private int employeeId;

    public EmployeeRequest(String firstname, String lastName, String email, int teamId, int employeeId) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.teamId = teamId;
        this.employeeId = employeeId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "RequestResponse{" +
                "firstname='" + firstname + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", teamId=" + teamId +
                ", employeeId=" + employeeId +
                '}';
    }

    public Employee getEmployeeInstance(EmployeeRequest employeeRequest)
    {
        Employee returnEmployee = new Employee(employeeRequest.firstname, employeeRequest.lastName, employeeRequest.email);
        return returnEmployee;
    }
}
