package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.dao.BlackListRepository;
import cdw.springTraining.gatekeeper.dao.RegistrationApprovalListRepository;
import cdw.springTraining.gatekeeper.dao.UserRepository;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceImplTest {

    @InjectMocks
    private RegistrationServiceImpl registrationServiceImpl;

    @Mock
    private BlackListRepository blackListRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RegistrationApprovalListRepository registrationApprovalListRepository;

//    @Test
//    public void testAddUser()
//    {
//        String mail = "sakthi@gmail.com";
//        UserResponse userResponse = new UserResponse();
//        userResponse.setName("sakthi");
//        userResponse.setAge(21);
//        userResponse.setGender("male");
//        userResponse.setMail(mail);
//        userResponse.setPassword("abc");
//        userResponse.setRoleName("resident");
//        ControllerResponse controllerResponse = new ControllerResponse();
//        controllerResponse.setMessage("User request has been successfully saved");
//        when(userRepository.findByMail(null)).thenReturn(null);
//        assertEquals(controllerResponse, registrationServiceImpl.addUser(userResponse));
//    }
}
