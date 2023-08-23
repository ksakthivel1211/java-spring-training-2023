package com.sakthivel.spring.boot.crudDemo.dao;

import com.sakthivel.spring.boot.crudDemo.entity.Room;
import com.sakthivel.spring.boot.crudDemo.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    public Room findByRoomName(String roomName);
}
