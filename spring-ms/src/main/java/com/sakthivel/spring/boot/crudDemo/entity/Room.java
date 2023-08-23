package com.sakthivel.spring.boot.crudDemo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private int roomId;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "room_capacity")
    private int roomCapacity;

    @ManyToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinTable(
            name = "timeSlot_room",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "time_slot_id")
    )
    private List<TimeSlot> timeSlots;

    public Room(){}

    public Room(String roomName, int roomCapacity) {
        this.roomName = roomName;
        this.roomCapacity = roomCapacity;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public void addTimeSlots(TimeSlot theTimeSlot)
    {
        if(timeSlots==null)
        {
            timeSlots = new ArrayList<>();
        }
        timeSlots.add(theTimeSlot);
    }
    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", roomCapacity=" + roomCapacity +
                '}';
    }
}
