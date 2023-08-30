package com.sakthivel.spring.boot.crudDemo.resposeModel;

import java.util.List;

public class EmployeeResponse {

    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private List<TeamResponse> teams;
    private List<TimeSlotResponse> timeSlots;

    public EmployeeResponse(int employeeId, String firstName, String lastName, String email) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public List<TeamResponse> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamResponse> teams) {
        this.teams = teams;
    }

    public List<TimeSlotResponse> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlotResponse> timeSlots) {
        this.timeSlots = timeSlots;
    }

    @Override
    public String toString() {
        return "EmployeeResponse{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", teams=" + teams +
                ", timeSlots=" + timeSlots +
                '}';
    }
}
