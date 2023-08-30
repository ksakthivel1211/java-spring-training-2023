package com.sakthivel.spring.boot.crudDemo.resposeModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TimeSlotResponse {

    private int TimeSlotId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private boolean booked;
    private String description;
    private List<RoomResponse> rooms;
    private List<TeamResponse> teams;
    private EmployeeResponse employee;


    public TimeSlotResponse(int timeSlotId, LocalTime startTime, LocalTime endTime, LocalDate date, boolean booked, String description) {
        this.TimeSlotId = timeSlotId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.booked = booked;
        this.description = description;

    }

    public int getTimeSlotId() {
        return TimeSlotId;
    }

    public void setTimeSlotId(int timeSlotId) {
        TimeSlotId = timeSlotId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RoomResponse> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomResponse> rooms) {
        this.rooms = rooms;
    }

    public List<TeamResponse> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamResponse> teams) {
        this.teams = teams;
    }

    public EmployeeResponse getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeResponse employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "TimeSlotResponse{" +
                "TimeSlotId=" + TimeSlotId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", date=" + date +
                ", booked=" + booked +
                ", description='" + description + '\'' +
                ", rooms=" + rooms +
                ", teams=" + teams +
                ", employee=" + employee +
                '}';
    }
}
