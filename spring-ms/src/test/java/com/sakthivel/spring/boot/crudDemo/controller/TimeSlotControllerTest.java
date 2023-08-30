package com.sakthivel.spring.boot.crudDemo.controller;

import com.sakthivel.spring.boot.crudDemo.entity.TimeSlot;
import com.sakthivel.spring.boot.crudDemo.requestModel.TimeSlotRequest;
import com.sakthivel.spring.boot.crudDemo.resposeModel.ControllerResponse;
import com.sakthivel.spring.boot.crudDemo.resposeModel.TimeSlotResponse;
import com.sakthivel.spring.boot.crudDemo.services.TimeSlotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TimeSlotControllerTest {

    @InjectMocks
    private TimeSlotController timeSlotController;

    @Mock
    private TimeSlotService timeSlotService;


    @Test
    public void testDeleteTimeSlot()
    {
        int timeSlotId = 1;
        ControllerResponse response = new ControllerResponse("Deleted time slot of id - " + timeSlotId);
        when(timeSlotService.deleteBooking(timeSlotId)).thenReturn(response);

        ResponseEntity<ControllerResponse> controllerResponse = timeSlotController.deleteTimeSlot(timeSlotId);
        assertEquals(controllerResponse,response);
    }

    @Test
    public void testAddTimeSlot()
    {
        int timeSlotId = 1;
        int employeeID = 1;
        int teamId = 1;
        List<Integer> empList = new ArrayList<Integer>() {{
            add(1);
            add(2);
        } };
        String roomName = "chennai";
        LocalDate date = LocalDate.of(2023,9,23);
        LocalTime startTime = LocalTime.of(11,00,00);
        LocalTime endTime = LocalTime.of(12,00,00);
        TimeSlot newTimeSlot = new TimeSlot(startTime,endTime,date,true,"scrum");
        TimeSlotRequest timeSlotRequest = new TimeSlotRequest(startTime,endTime,date,true,"scrum",employeeID,empList,roomName,teamId,timeSlotId);

        ControllerResponse response = new ControllerResponse("Room was booked");
        when(timeSlotService.TeamBooking(newTimeSlot,1,1,"chennai")).thenReturn(response);

        ResponseEntity<ControllerResponse> controllerResponse = timeSlotController.addTimeSlot(timeSlotRequest);
        assertEquals(controllerResponse,response);
    }
}



















