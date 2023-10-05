package cdw.springTraining.gatekeeper.controller;

import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.UserResponse;
import cdw.springTraining.gatekeeper.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest {

    @InjectMocks
    private RegistrationController registrationController;

    @Mock
    private RegistrationServiceImpl registrationServiceImpl;

    @Test
    public void registrationRequestTest()
    {
        UserResponse userResponse = new UserResponse();
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("User request has been successfully saved");
        when(registrationServiceImpl.addUser(userResponse)).thenReturn(controllerResponse);
        assertEquals(controllerResponse,registrationController.registrationRequest(userResponse).getBody());
    }
}
