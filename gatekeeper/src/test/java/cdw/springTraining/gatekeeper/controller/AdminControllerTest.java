package cdw.springTraining.gatekeeper.controller;

import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.RegistrationResponse;
import cdw.springTraining.gatekeeper.model.UserResponse;
import cdw.springTraining.gatekeeper.service.AdminServiceImpl;
import cdw.springTraining.gatekeeper.model.ApprovalRequest;
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
    private AdminServiceImpl adminServiceImpl;

    @Test
    public void testGetAllRequest(){

        List<RegistrationResponse>  approvalLists= new ArrayList<>();

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
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("User has been deleted successfully");
        when(adminServiceImpl.deleteUser(userId)).thenReturn(controllerResponse);
        ResponseEntity<ControllerResponse> deleteUserResponse = adminController.deleteResident(userId);
        assertEquals(controllerResponse,deleteUserResponse.getBody());
    }

    @Test
    public void testUpdateResident()
    {
        UserResponse user = new UserResponse();
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("User details has been updated");
        when(adminServiceImpl.updateUser(user)).thenReturn(controllerResponse);
        ResponseEntity<ControllerResponse> updateUserResponse = adminController.updateResident(user);
        assertEquals(controllerResponse,updateUserResponse.getBody());
    }


}
