package com.sakthivel.spring.boot.crudDemo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_id")
    private int employeeId;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinTable(
            name = "employee_team",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Teams> teams;

    @OneToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH},
            mappedBy = "employee")
    private List<TimeSlot> timeSlots;

    public Employee()
    {

    }

    public Employee(String firstName, String lastName, String email) {
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

    public List<Teams> getTeams() {
        return teams;
    }

    public void setTeams(List<Teams> teams) {
        this.teams = teams;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }


    /**
     * addTeams method adds teams object given in the parameter to the Teams list
     * @param theTeams - method receives parameter with type Teams
     */
    public void addTeams(Teams theTeams)
    {
        if(teams==null)
        {
            teams= new ArrayList<>();
        }
        teams.add(theTeams);
    }

    /**
     * addTimeSlots method adds time slot object given in the parameter to the Time slot list
     * @param theTimeSlot - method receives parameter with type TimeSlot
     */
    public void addTimeSlots(TimeSlot theTimeSlot)
    {
        if(timeSlots==null)
        {
            timeSlots= new ArrayList<>();
        }
        timeSlots.add(theTimeSlot);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
