package com.sakthivel.spring.boot.crudDemo.respose;

import com.sakthivel.spring.boot.crudDemo.entity.Employee;
import com.sakthivel.spring.boot.crudDemo.entity.TimeSlot;

import java.util.List;

public class TeamResponse {

    private int teamId;
    private String teamName;
    private int teamCount;

    private List<EmployeeResponse> employees;
    private List<TimeSlotResponse> timeSlots;

    public TeamResponse(int teamId, String teamName, int teamCount) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamCount = teamCount;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }

    public List<EmployeeResponse> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeResponse> employees) {
        this.employees = employees;
    }

    public List<TimeSlotResponse> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlotResponse> timeSlots) {
        this.timeSlots = timeSlots;
    }

    @Override
    public String toString() {
        return "TeamResponse{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", teamCount=" + teamCount +
                ", employees=" + employees +
                ", timeSlots=" + timeSlots +
                '}';
    }
}
