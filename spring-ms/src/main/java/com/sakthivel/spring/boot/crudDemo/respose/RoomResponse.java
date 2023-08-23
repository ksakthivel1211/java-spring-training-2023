package com.sakthivel.spring.boot.crudDemo.respose;

import com.sakthivel.spring.boot.crudDemo.entity.TimeSlot;

import java.util.List;

public class RoomResponse {

    private int roomId;
    private String roomName;
    private int roomCapacity;
    private List<TimeSlotResponse> timeSlots;

    public RoomResponse(int roomId, String roomName, int roomCapacity) {
        this.roomId = roomId;
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

    public List<TimeSlotResponse> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlotResponse> timeSlots) {
        this.timeSlots = timeSlots;
    }

    @Override
    public String toString() {
        return "RoomResponse{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", roomCapacity=" + roomCapacity +
                ", timeSlots=" + timeSlots +
                '}';
    }
}
