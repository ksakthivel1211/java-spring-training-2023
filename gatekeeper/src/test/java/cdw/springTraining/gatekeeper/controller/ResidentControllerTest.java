package cdw.springTraining.gatekeeper.controller;

import cdw.springTraining.gatekeeper.model.BlackListRequest;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.VisitorSlotRequest;
import cdw.springTraining.gatekeeper.service.BlackListServiceImpl;
import cdw.springTraining.gatekeeper.service.ResidentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static cdw.springTraining.gatekeeper.constant.SuccessConstants.RESIDENT_CHECKED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResidentControllerTest {

    @InjectMocks
    private ResidentController residentController;

    @Mock
    private ResidentServiceImpl residentServiceImpl;

    @Mock
    private BlackListServiceImpl blackListServiceImpl;

    @Test
    public void testBookVisitorSlot(){
        VisitorSlotRequest visitorSlotRequest = new VisitorSlotRequest();
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("Slot for the visitor has been booked");
        when(residentServiceImpl.bookVisitorSlot(visitorSlotRequest)).thenReturn(controllerResponse);
        assertEquals(controllerResponse,residentController.bookVisitorSlot(visitorSlotRequest).getBody());
    }

    @Test
    public void testRemoveVisitor()
    {
        int slotId = 1;
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("The visitor slot has been cancelled");
        when(residentServiceImpl.removeVisitorSlot(slotId)).thenReturn(controllerResponse);
        assertEquals(controllerResponse,residentController.removeVisitorSlot(slotId).getBody());
    }

    @Test
    public void testResidentBlackList()
    {
        BlackListRequest blackListRequest = new BlackListRequest();
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("The user has been black listed");
        when(blackListServiceImpl.addToBlackList(blackListRequest)).thenReturn(controllerResponse);
        assertEquals(controllerResponse,residentController.residentBlackList(blackListRequest).getBody());
    }

    @Test
    public void testResidentCheckIn()
    {
        int userId = 1;
        String checked = "in";
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage(RESIDENT_CHECKED+checked);
        when(residentServiceImpl.userChecked(checked)).thenReturn(controllerResponse);
        assertEquals(controllerResponse,residentController.residentCheckingIn().getBody());
    }

    @Test
    public void testResidentCheckOut()
    {
        int userId = 1;
        String checked = "out";
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage(RESIDENT_CHECKED+checked);
        when(residentServiceImpl.userChecked(checked)).thenReturn(controllerResponse);
        assertEquals(controllerResponse,residentController.residentCheckingOut().getBody());
    }

}
