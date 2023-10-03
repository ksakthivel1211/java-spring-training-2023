package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.dao.BlackListRepository;
import cdw.springTraining.gatekeeper.dao.RegistrationApprovalListRepository;
import cdw.springTraining.gatekeeper.dao.UserRepository;
import cdw.springTraining.gatekeeper.entity.RegistrationApprovalList;
import cdw.springTraining.gatekeeper.entity.User;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private RegistrationApprovalListRepository registrationApprovalListRepository;

    @Mock
    private BlackListRepository blackListRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    public void testListAll()
    {
        List<RegistrationResponse> registrationResponses = new ArrayList<>();
        List<RegistrationApprovalList>  approvalLists= new ArrayList<>();

        when(registrationApprovalListRepository.findAll()).thenReturn(approvalLists);

        assertEquals(registrationResponses,adminService.listAllRequest());
    }

    @Test
    public void testGrantUserRequest()
    {
        int requestId = 1;
        RegistrationApprovalList request = new RegistrationApprovalList();
        when(registrationApprovalListRepository.findById(requestId)).thenReturn(Optional.of(request));
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("Request has been accepted");
        assertEquals(controllerResponse,adminService.grantUserRequest(requestId));
    }

    @Test void testRejectUserReject()
    {
        int requestId = 1;
        RegistrationApprovalList request = new RegistrationApprovalList();
        when(registrationApprovalListRepository.findById(requestId)).thenReturn(Optional.of(request));
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("Request has been rejected");
        assertEquals(controllerResponse,adminService.rejectUserRequest(requestId));
    }

    @Test void testDeleteUser()
    {
        int userId = 1;
        User user = new User("sakthi",21,"male","sakthi@gmail.com","abc","resident");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("User has been deleted successfully");
        assertEquals(controllerResponse,adminService.deleteUser(userId));
    }

    @Test void testUpdateUser()
    {
        String mail =  "sakthi@gmail.com";
        User user = new User("sakthi",21,"male","sakthi@gmail.com","abc","resident");
        when(userRepository.findByMail(mail)).thenReturn(Optional.of(user));
        UserResponse userResponse = new UserResponse();
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("User details has been updated");
        userResponse.setName("sakthi");
        userResponse.setAge(21);
        userResponse.setGender("male");
        userResponse.setMail("sakthi@gmail.com");
        userResponse.setPassword("abc");
        userResponse.setRoleName("resident");
        assertEquals(controllerResponse,adminService.updateUser(userResponse));
    }


}
