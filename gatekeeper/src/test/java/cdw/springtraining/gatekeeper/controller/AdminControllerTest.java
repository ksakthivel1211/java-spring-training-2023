package cdw.springtraining.gatekeeper.controller;


import cdw.springtraining.gatekeeper.service.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.ApprovalRequest;
import cdw.springtraining.gatekeeper.model.UserResponse;
import cdw.springtraining.gatekeeper.model.RegistrationResponse;
import org.springframework.http.HttpStatus;
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
    private AdminServiceImpl adminServiceImpl;

    @Test
    public void testGetAllRequest(){

        List<RegistrationResponse> approvalLists= new ArrayList<>();

        when(adminServiceImpl.listAllRequest()).thenReturn(approvalLists);

        assertEquals(approvalLists,adminController.viewRequest().getBody());
    }

    @Test
    public void testApproveRequest()
    {
        int requestId = 12;
        ApprovalRequest request = new ApprovalRequest();
        request.setRequestId(requestId);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("Request has been accepted");
        when(adminServiceImpl.grantUserRequest(requestId)).thenReturn(controllerResponse);

        ResponseEntity<ControllerResponse> approvalResponse = adminController.approveRequest(request);
        assertEquals(controllerResponse,approvalResponse.getBody());
    }

    @Test
    public void testRejectRequest()
    {
        int requestId = 12;
        ApprovalRequest request = new ApprovalRequest();
        request.setRequestId(requestId);
        ControllerResponse controllerResponse = new ControllerResponse();

        controllerResponse.setMessage("Request has been rejected");

        when(adminServiceImpl.rejectUserRequest(requestId)).thenReturn(controllerResponse);

        ResponseEntity<ControllerResponse> rejectResponse = adminController.rejectRequest(request);

        assertEquals(controllerResponse,rejectResponse.getBody());
    }

    @Test
    public void testDeleteResident()
    {
        int userId = 12;
        ResponseEntity<Void> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        ResponseEntity<Void> deleteUserResponse = adminController.deleteResident(userId);

        assertEquals(response.getStatusCode(),deleteUserResponse.getStatusCode());
    }

    @Test
    public void testUpdateResident()
    {
        UserResponse user = new UserResponse();
        ResponseEntity<Void> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        ResponseEntity<Void> updateResident = adminController.updateResident(user);
        assertEquals(response.getStatusCode(),updateResident.getStatusCode());
    }


}
