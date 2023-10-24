package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.UserResponse;
import cdw.springtraining.gatekeeper.dao.BlackListRepository;
import cdw.springtraining.gatekeeper.dao.RegistrationApprovalListRepository;
import cdw.springtraining.gatekeeper.dao.UserRepository;
import cdw.springtraining.gatekeeper.service.RegistrationServiceImpl;
import cdw.springtraining.gatekeeper.utils.UserInformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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

    @Test
    public void testAddUser()
    {
        String mail = "sakthi@gmail.com";
        UserResponse userResponse = new UserResponse();
        userResponse.setName("sakthi");
        userResponse.setAge(21);
        userResponse.setGender("male");
        userResponse.setMail(mail);
        userResponse.setPassword("abc");
        userResponse.setRoleName("resident");
        MockedStatic mockedStatic = Mockito.mockStatic(UserInformation.class);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("User request has been successfully saved");
        when(UserInformation.getUserName()).thenReturn(mail);
        when(userRepository.findByMail(mail)).thenReturn(Optional.empty());


        assertEquals(controllerResponse, registrationServiceImpl.addUser(userResponse));
        mockedStatic.close();
    }
}
