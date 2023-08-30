package com.sakthivel.spring.boot.crudDemo.controller;

import com.sakthivel.spring.boot.crudDemo.resposeModel.ControllerResponse;
import com.sakthivel.spring.boot.crudDemo.services.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeRestControllerTest {

    @InjectMocks
    private RoomController roomController;

    @Mock
    private RoomService roomService;

    @Test
    public void testDeleteRoom()
    {
        int roomId = 8;
        ControllerResponse newResponse = new ControllerResponse("room deleted");
        when(roomService.deleteRoom(8)).thenReturn(newResponse);
        String response = String.valueOf(roomController.deleteRoom(roomId));
        assertEquals("room deleted",response);
    }

}