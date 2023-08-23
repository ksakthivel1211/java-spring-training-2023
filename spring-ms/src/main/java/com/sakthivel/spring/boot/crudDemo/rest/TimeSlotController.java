package com.sakthivel.spring.boot.crudDemo.rest;

import com.sakthivel.spring.boot.crudDemo.respose.TimeSlotResponse;
import com.sakthivel.spring.boot.crudDemo.services.TimeSlotService;
import com.sakthivel.spring.boot.crudDemo.entity.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meeting/time_slot")
public class TimeSlotController {

    private TimeSlotService timeSlotService;

    @Autowired
    public TimeSlotController(TimeSlotService theTimeSlotService){
        timeSlotService = theTimeSlotService;
    }

    @GetMapping("/")
    public List<TimeSlotResponse> findAllTimeSlots()
    {
        return timeSlotService.getAllTimeSlots();
    }

    @GetMapping("/{timeSlotId}")
    public TimeSlotResponse getTimeSlot(@PathVariable int timeSlotId)
    {

        return timeSlotService.getTimeSlotWithId(timeSlotId);
    }

    @PostMapping("/collaboration/{employeeId}/{teamId}/{employeeIds}")
    public String addTimeSlotForCollaboration(@RequestBody TimeSlot theTimeSlot,@PathVariable int employeeId,@PathVariable List<Integer> employeeIds,@RequestParam String roomName)
    {
        return timeSlotService.collaborationBooking(theTimeSlot,employeeId,employeeIds,roomName);
    }

    @PostMapping("/{employeeId}/{teamId}")
    public String addTimeSlot(@RequestBody TimeSlot theTimeSlot, @PathVariable int employeeId, @PathVariable int teamId,@RequestParam String roomName)
    {
        return timeSlotService.TeamBooking(theTimeSlot,employeeId,teamId,roomName);
    }


    @PutMapping("/{timeSlotId}")
    public String updateRoom(@RequestBody TimeSlot theTimeSlot, @PathVariable int timeSlotId)
    {
        return timeSlotService.updateBooking(theTimeSlot,timeSlotId);
    }

    @DeleteMapping("/{timeSlotId}")
    public String deleteRoom(@PathVariable int timeSlotId)
    {

        return timeSlotService.deleteBooking(timeSlotId);
    }

}