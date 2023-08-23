package com.sakthivel.spring.boot.crudDemo.entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "time_slot")
public class TimeSlot {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_slot_id")
    private int timeSlotId;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "booked")
    private boolean booked;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinTable(
            name = "team_timeSlot",
            joinColumns = @JoinColumn(name = "time_slot_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Teams> teams;

    @ManyToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinTable(
            name = "timeSlot_room",
            joinColumns = @JoinColumn(name = "time_slot_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private List<Room> rooms;

    @ManyToOne(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "employee")
    private Employee employee;

    public TimeSlot(){}

    public TimeSlot(LocalTime startTime, LocalTime endTime, LocalDate date, boolean booked, String description) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.booked = booked;
        this.description= description;
    }

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public boolean getBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public List<Teams> getTeams() {
        return teams;
    }

    public void setTeams(List<Teams> teams) {
        this.teams = teams;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void addTeams(Teams theTeams)
    {
        if(teams==null)
        {
            teams = new ArrayList<>();
        }
        teams.add(theTeams);
    }

    public void addRoom(Room theRoom)
    {
        if(rooms==null)
        {
            rooms= new ArrayList<>();
        }
        rooms.add(theRoom);
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "timeSlotId=" + timeSlotId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", date=" + date +
                ", booked=" + booked +
                ", description=" + description +
                '}';
    }

}
