package com.sakthivel.spring.boot.crudDemo.requestModel;

import com.sakthivel.spring.boot.crudDemo.entity.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TimeSlotRequest {

    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private boolean booked;
    private String description;
    private int employeeId;
    private List<Integer> employeeIds;
    private String roomName;
    private int teamId;
    private int timeSlotId;

    public TimeSlotRequest(LocalTime startTime, LocalTime endTime, LocalDate date, boolean booked, String description, int employeeId, List<Integer> employeeIds, String roomName,int teamId,int timeSlotId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.booked = booked;
        this.description = description;
        this.employeeId = employeeId;
        this.employeeIds = employeeIds;
        this.roomName = roomName;
        this.teamId = teamId;
        this.timeSlotId = timeSlotId;
    }

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
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

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public List<Integer> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Integer> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return "TimeSlotRequest{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", date=" + date +
                ", booked=" + booked +
                ", description='" + description + '\'' +
                ", employeeId=" + employeeId +
                ", employeeIds=" + employeeIds +
                ", roomName='" + roomName + '\'' +
                '}';
    }

    public TimeSlot getTimeSlotInstance(TimeSlotRequest timeSlotRequest)
    {
        TimeSlot returnTimeSlot = new TimeSlot(timeSlotRequest.startTime,timeSlotRequest.endTime,timeSlotRequest.date,timeSlotRequest.booked,timeSlotRequest.description);
        return returnTimeSlot;
    }
}
