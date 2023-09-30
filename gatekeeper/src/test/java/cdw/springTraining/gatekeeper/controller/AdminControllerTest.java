package cdw.springTraining.gatekeeper.controller;

import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.RegistrationResponse;
import cdw.springTraining.gatekeeper.model.UserResponse;
import cdw.springTraining.gatekeeper.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;

    @Test
    public void testGetAllRequest(){

        List<RegistrationResponse>  approvalLists= new ArrayList<>();

        when(adminService.listAllRequest()).thenReturn(approvalLists);

        assertEquals(approvalLists,adminController.viewRequest().getBody());
    }

    @Test
    public void testApproveRequest()
    {
        int requestId = 12;
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("Request has been accepted");
        when(adminService.grantUserRequest(requestId)).thenReturn(controllerResponse);

        ResponseEntity<ControllerResponse> approvalResponse = adminController.approveRequest(requestId);
        assertEquals(controllerResponse,approvalResponse.getBody());
    }

    @Test
    public void testRejectRequest()
    {
        int requestId = 12;
        ControllerResponse controllerResponse = new ControllerResponse();

        controllerResponse.setMessage("Request has been rejected");

        when(adminService.rejectUserRequest(requestId)).thenReturn(controllerResponse);

        ResponseEntity<ControllerResponse> rejectResponse = adminController.rejectRequest(requestId);

        assertEquals(controllerResponse,rejectResponse.getBody());
    }

    @Test
    public void testDeleteResident()
    {
        int userId = 12;
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("User has been deleted successfully");
        when(adminService.deleteUser(userId)).thenReturn(controllerResponse);
        ResponseEntity<ControllerResponse> deleteUserResponse = adminController.deleteResident(userId);
        assertEquals(controllerResponse,deleteUserResponse.getBody());
    }

    @Test
    public void testUpdateResident()
    {
        UserResponse user = new UserResponse();
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("User details has been updated");
        when(adminService.updateUser(user)).thenReturn(controllerResponse);
        ResponseEntity<ControllerResponse> updateUserResponse = adminController.updateResident(user);
        assertEquals(controllerResponse,updateUserResponse.getBody());
    }


}
