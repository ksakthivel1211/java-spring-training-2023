package com.sakthivel.spring.boot.crudDemo.requestModel;

import com.sakthivel.spring.boot.crudDemo.entity.Room;
import org.springframework.stereotype.Service;

@Service
public class RoomRequest {

    private String roomName;
    private int roomCapacity;
    private int roomId;

    public RoomRequest(String roomName, int roomCapacity, int roomId) {
        this.roomName = roomName;
        this.roomCapacity = roomCapacity;
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

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "RoomRequest{" +
                "roomName='" + roomName + '\'' +
                ", roomCapacity=" + roomCapacity +
                ", roomId=" + roomId +
                '}';
    }

    public Room getRoomInstance(RoomRequest roomRequest)
    {
        Room room = new Room(roomRequest.roomName,roomRequest.roomCapacity);
        return room;
    }
}
