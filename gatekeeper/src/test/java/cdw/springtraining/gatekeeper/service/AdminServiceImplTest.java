package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.dao.BlackListRepository;
import cdw.springtraining.gatekeeper.dao.RegistrationApprovalListRepository;
import cdw.springtraining.gatekeeper.dao.UserRepository;
import cdw.springtraining.gatekeeper.entity.RegistrationApprovalList;
import cdw.springtraining.gatekeeper.entity.User;
import cdw.springtraining.gatekeeper.service.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import cdw.springtraining.gatekeeper.model.RegistrationResponse;
import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.UserResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    @Mock
    private RegistrationApprovalListRepository registrationApprovalListRepository;

    @Mock
    private BlackListRepository blackListRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminServiceImpl adminServiceImpl;

    @Test
    public void testListAll()
    {
        List<RegistrationResponse> registrationResponses = new ArrayList<>();

        List<RegistrationApprovalList>  approvalLists= new ArrayList<>();

        RegistrationApprovalList list = new RegistrationApprovalList();

        list.setAge(21);
        list.setApproval_id(1);
        list.setStatus("approved");
        list.setGender("male");
        list.setName("sakthi");
        list.setPassword("abc123");
        list.setMail("sakthi@gmail.com");
        list.setRoleName("resident");
        approvalLists.add(list);

        RegistrationResponse tempResponse = new RegistrationResponse();
        tempResponse.setAge(list.getAge());
        tempResponse.setId(list.getApproval_id());
        tempResponse.setStatus(list.getStatus());
        tempResponse.setName(list.getName());
        tempResponse.setGender(list.getGender());
        tempResponse.setMail(list.getMail());
        tempResponse.setRoleName(list.getRoleName());
        registrationResponses.add(tempResponse);

        when(registrationApprovalListRepository.findAll()).thenReturn(approvalLists);

        assertEquals(registrationResponses, adminServiceImpl.listAllRequest());
    }

    @Test
    public void testGrantUserRequest()
    {
        int requestId = 1;
        RegistrationApprovalList request = new RegistrationApprovalList();
        when(registrationApprovalListRepository.findById(requestId)).thenReturn(Optional.of(request));
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("Request has been accepted and user has been added");
        assertEquals(controllerResponse, adminServiceImpl.grantUserRequest(requestId));
    }

    @Test void testRejectUserReject()
    {
        int requestId = 1;
        RegistrationApprovalList request = new RegistrationApprovalList();
        when(registrationApprovalListRepository.findById(requestId)).thenReturn(Optional.of(request));
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("Request has been rejected");
        assertEquals(controllerResponse, adminServiceImpl.rejectUserRequest(requestId));
    }

    @Test void testDeleteUser()
    {
        int userId = 1;
        User user = new User("sakthi",21,"male","sakthi@gmail.com","abc","resident");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("User has been deleted successfully");
        assertEquals(controllerResponse, adminServiceImpl.deleteUser(userId));
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
        assertEquals(controllerResponse, adminServiceImpl.updateUser(userResponse));
    }


}
