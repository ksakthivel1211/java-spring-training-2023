package com.sakthivel.spring.boot.crudDemo.controller;

import com.sakthivel.spring.boot.crudDemo.requestModel.TimeSlotRequest;
import com.sakthivel.spring.boot.crudDemo.resposeModel.ControllerResponse;
import com.sakthivel.spring.boot.crudDemo.resposeModel.TimeSlotResponse;
import com.sakthivel.spring.boot.crudDemo.services.TimeSlotService;
import com.sakthivel.spring.boot.crudDemo.entity.TimeSlot;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 *
 *
 *
 */
@RestController
@RequestMapping("/meeting/time_slot")
public class TimeSlotController {

    private TimeSlotService timeSlotService;
    private TimeSlotRequest timeSlotRequest;

    @Autowired
    public TimeSlotController(TimeSlotService theTimeSlotService,TimeSlotRequest theTimeSlotRequest){
        timeSlotService = theTimeSlotService;
        timeSlotRequest = theTimeSlotRequest;
    }

    /**
     * findAllTimeSlot method is the endpoint for getting all the Time slot details
     * @return - returns List of time slot response object
     */
    @GetMapping("/")
    public ResponseEntity<List<TimeSlotResponse>> findAllTimeSlots()
    {
        return ResponseEntity.status(HttpStatus.OK).body(timeSlotService.getAllTimeSlots());
    }

    /**
     * getTimeSlot method is the endpoint for getting Time slot details whose id is given
     * @param timeSlotId - time slot id of type integer for which the time slot object has to be found
     * @return - returns object of type TimeSlotResponse
     */
    @GetMapping("/{timeSlotId}")
    public ResponseEntity<TimeSlotResponse> getTimeSlot(@PathVariable int timeSlotId)
    {

        return ResponseEntity.status(HttpStatus.OK).body(timeSlotService.getTimeSlotWithId(timeSlotId));
    }

    /**
     * addTimeSlotForCollaboration method is used to book meetings exclusive to team booking
     * @param theTimeSlotRequest - Request body is passed as parameter of type TimeSlotRequest
     * @return - returns response object of type ControllerResponse
     */
    @PostMapping("/collaboration/{employeeId}/{teamId}/{employeeIds}")
    public ResponseEntity<ControllerResponse> addTimeSlotForCollaboration(@RequestBody TimeSlotRequest theTimeSlotRequest)
    {
        return ResponseEntity.status(HttpStatus.OK).body(timeSlotService.collaborationBooking(timeSlotRequest.getTimeSlotInstance(theTimeSlotRequest),theTimeSlotRequest.getEmployeeId(),theTimeSlotRequest.getEmployeeIds(),theTimeSlotRequest.getRoomName()));
    }

    /**
     *
     * @param theTimeSlotRequest - Request body is passed as parameter of type TimeSlotRequest
     * @returnreturns returns response object of type ControllerResponse
     */


    @PostMapping("/{employeeId}/{teamId}")
    public ResponseEntity<ControllerResponse> addTimeSlot(@RequestBody TimeSlotRequest theTimeSlotRequest)
    {
        return ResponseEntity.status(HttpStatus.OK).body(timeSlotService.TeamBooking(timeSlotRequest.getTimeSlotInstance(theTimeSlotRequest),theTimeSlotRequest.getEmployeeId(),theTimeSlotRequest.getTeamId(),theTimeSlotRequest.getRoomName()));
    }

    /**
     * updateTimeSlot method updates the timeSlot object to the DB
     * @param theTimeSlotRequest - Request body is passed as parameter of type TimeSlotRequest
     * @return returns response object of type ControllerResponse
     */
    @PutMapping("/{timeSlotId}")
    public ResponseEntity<ControllerResponse> updateTimeSlot(@RequestBody TimeSlotRequest theTimeSlotRequest)
    {
        return ResponseEntity.status(HttpStatus.OK).body(timeSlotService.updateBooking(timeSlotRequest.getTimeSlotInstance(theTimeSlotRequest),theTimeSlotRequest.getTimeSlotId()));
    }

    /**
     * deleteTimeSlot method deletes the timeSlot
     * @param timeSlotId - timeSlotId of type int passed as parameter
     * @return returns response object of type ControllerResponse
     */
    @DeleteMapping("/{timeSlotId}")
    public ResponseEntity<ControllerResponse> deleteTimeSlot(@PathVariable int timeSlotId)
    {

        return ResponseEntity.status(HttpStatus.OK).body(timeSlotService.deleteBooking(timeSlotId));
    }

}