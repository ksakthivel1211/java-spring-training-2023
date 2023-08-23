package com.sakthivel.spring.boot.crudDemo.rest;

import com.sakthivel.spring.boot.crudDemo.entity.Room;
import com.sakthivel.spring.boot.crudDemo.respose.RoomResponse;
import com.sakthivel.spring.boot.crudDemo.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/meeting")
public class RoomController {

    private RoomService roomService;

    @Autowired
    public RoomController(RoomService theRoomService){
        roomService = theRoomService;
    }

    @GetMapping("/room")
    public List<RoomResponse> findAllRoom()
    {
        return roomService.getAllRooms();
    }

    @GetMapping("/room/{roomId}")
    public RoomResponse getRoom(@PathVariable int roomId)
    {
        return roomService.getRoomById(roomId);
    }

    @PostMapping("/room")
    public String addRoom(@RequestBody Room theRoom)
    {
        return roomService.addRoom(theRoom);
    }

    @PutMapping("/room/{roomId}")
    public String updateRoom(@RequestBody Room theRoom, @PathVariable int roomId)
    {
        return roomService.updateRoom(theRoom,roomId);
    }

    @DeleteMapping("/room/{roomId}")
    public String deleteRoom(@PathVariable int roomId)
    {
        return roomService.deleteRoom(roomId);
    }

}