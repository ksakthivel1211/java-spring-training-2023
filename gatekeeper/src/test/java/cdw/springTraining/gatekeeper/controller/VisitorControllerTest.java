package cdw.springTraining.gatekeeper.controller;

import cdw.springTraining.gatekeeper.entity.User;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.VisitorPassResponse;
import cdw.springTraining.gatekeeper.service.VisitorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VisitorControllerTest {

    @InjectMocks
    private VisitorController visitorController;

    @Mock
    private VisitorServiceImpl visitorService;

    @Test
    public void testGetPass()
    {
        String mail = "sakthi@gmail.com";
        VisitorPassResponse passResponse = new VisitorPassResponse();
        when(visitorService.keyGen(mail)).thenReturn(passResponse);
        assertEquals(passResponse,visitorController.getPass(mail).getBody());
    }

    @Test
    public void testResidentCheck()
    {
        String mail = "sakthi@gmail.com";
        User user = new User();
        user.setChecked("in");
        user.setMail(mail);
        String residentChecked = user.getChecked();
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("The resident has checked" + residentChecked);
        when(visitorService.checkResident(mail)).thenReturn(controllerResponse);
        assertEquals(controllerResponse,visitorController.residentCheck(mail).getBody());

    }
}
