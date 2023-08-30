package com.sakthivel.spring.boot.crudDemo.controller;

import com.sakthivel.spring.boot.crudDemo.entity.Room;
import com.sakthivel.spring.boot.crudDemo.requestModel.RoomRequest;
import com.sakthivel.spring.boot.crudDemo.resposeModel.ControllerResponse;
import com.sakthivel.spring.boot.crudDemo.resposeModel.RoomResponse;
import com.sakthivel.spring.boot.crudDemo.services.RoomService;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meeting")
public class RoomController {

    private RoomService roomService;

    private RoomRequest roomRequest;

    @Autowired
    public RoomController(RoomService theRoomService,RoomRequest theRoomRequest){
        roomService = theRoomService;
        roomRequest = theRoomRequest;
    }

    /**
     * findAllRoom method gives details of all room
     * @return - returns List of Room objects
     */
    @GetMapping("/room")
    public ResponseEntity<List<RoomResponse>> findAllRoom()
    {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.getAllRooms());
    }

    /**
     * getRoom method gives the details of room whose id is given
     * @param roomId - roomId of type int passed as parameter
     * @return - return room object whose id is given
     */
    @GetMapping("/room/{roomId}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable int roomId)
    {
        return  ResponseEntity.status(HttpStatus.OK).body(roomService.getRoomById(roomId));
    }

    /**
     * addRoom methods saves the room object to the DB
     * @param theRoom - room object from request body is passed as parameter
     * @return - returns response entity of type ControllerResponse for the confirmation of saving the object
     */
    @PostMapping("/room")
    public ResponseEntity<ControllerResponse> addRoom(@RequestBody Room theRoom)
    {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.addRoom(theRoom));
    }

    /**
     * updateRoom method updates the room object to the DB
     * @param theRoomRequest - Request body is passed as parameter of type RoomRequest
     * @return returns response object of type ControllerResponse
     */
    @PutMapping("/room/{roomId}")
    public ResponseEntity<ControllerResponse> updateRoom(@RequestBody RoomRequest theRoomRequest)
    {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.updateRoom(roomRequest.getRoomInstance(theRoomRequest),theRoomRequest.getRoomId()));
    }

    /**
     * deleteRoom method deletes the room instance from the DB
     * @param roomId - roomId of type int passed as parameter
     * @return returns response object of type ControllerResponse
     */
    @DeleteMapping("/room/{roomId}")
    public ResponseEntity<ControllerResponse> deleteRoom(@PathVariable int roomId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.deleteRoom(roomId));
    }

}