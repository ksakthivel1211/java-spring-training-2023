package com.sakthivel.spring.boot.crudDemo.services;

import com.sakthivel.spring.boot.crudDemo.dao.RoomRepository;
import com.sakthivel.spring.boot.crudDemo.entity.Room;
import com.sakthivel.spring.boot.crudDemo.entity.TimeSlot;
import com.sakthivel.spring.boot.crudDemo.respose.RoomResponse;
import com.sakthivel.spring.boot.crudDemo.respose.TimeSlotResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository theRoomRepository){
        roomRepository=theRoomRepository;
    }

    /**
     * getAllRooms method returns all the existing rooms
     * @return - returns List of all rooms object
     */
    public List<RoomResponse> getAllRooms()
    {
        List<Room> rooms = roomRepository.findAll();
        List<RoomResponse> roomResponses = rooms.stream().map(theRooms->{
            return getRoomResponse(theRooms);
        }).collect(Collectors.toList());

        return roomResponses;
    }

    /**
     * getRoomById method returns room details (Room object) whose id is given
     * @param roomId - room id with type int passed as parameter which room details should be found
     * @return returns the room Object of the given room id
     */
    public RoomResponse getRoomById(int roomId)
    {
        Optional<Room> result = roomRepository.findById(roomId);

        Room theRoom = null;
        if(result.isPresent())
        {
            theRoom=result.get();
            return getRoomResponse(theRoom);
        }
        else {
            throw new RuntimeException("Room id not found :"+roomId);
        }

    }

    /**
     * addRoom method adds the room details and room id to the DB using the save method in room repository
     * @param theRoom - Room object type passed as parameter which is added to the DB
     * @return returns the Room object which has been saved in the DB
     */
    public String addRoom(Room theRoom)
    {
        theRoom.setRoomId(0);
        Room dbRoom = roomRepository.save(theRoom);
        return "Room details saved";
    }

    /**
     * updateRoom method updates the existing Room details in the DB by fetching the Existing room details using the room id and saving the new room object to the DB again
     * @param theRoom - parameter of type Room is passed as parameter which is the new room object to be updated in the DB
     * @param roomId - room id is a parameter of type int which determines which room object to be retrieved to be updated
     * @return - returns the Room object which has been saved in the DB
     */
    public String updateRoom(Room theRoom,int roomId)
    {
        theRoom.setRoomId(roomId);
        Room dbRoom = roomRepository.save(theRoom);
        return "Room details updated on id : "+roomId;
    }

    /**
     * deleteRoom method deletes the room instance from the DB by retrieving the room details using the room id and deleting it using the deleteById method in the room repository
     * @param roomId - employee id is a parameter of type int which determines which employee object to be retrieved to be deleted
     * @return - returns string message whether the employee instance is deleted or not
     */
    public String deleteRoom(int roomId)
    {
        Optional<Room> result = roomRepository.findById(roomId);

        if(result.isPresent())
        {
            roomRepository.deleteById(roomId);
        }
        else {
            throw new RuntimeException("Room id not found :"+roomId);
        }
        return "Deleted room of id - "+ roomId;
    }

    public RoomResponse getRoomResponse(Room theRoom)
    {
        RoomResponse roomResponse = new RoomResponse(theRoom.getRoomId(),theRoom.getRoomName(),theRoom.getRoomCapacity());

        List<TimeSlot> empTimeSlots = theRoom.getTimeSlots();
        List<TimeSlotResponse> timeSlotResponses = empTimeSlots.stream().map(theTimeSlot->{
            TimeSlotResponse returnTimeSlot = new TimeSlotResponse(theTimeSlot.getTimeSlotId(),theTimeSlot.getStartTime(),theTimeSlot.getEndTime(),theTimeSlot.getDate(),theTimeSlot.getBooked(),theTimeSlot.getDescription());
            return returnTimeSlot;
        }).collect(Collectors.toList());

        roomResponse.setTimeSlots(timeSlotResponses);
        return roomResponse;
    }
}
