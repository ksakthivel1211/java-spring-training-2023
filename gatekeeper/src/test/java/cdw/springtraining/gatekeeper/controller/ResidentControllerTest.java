package cdw.springtraining.gatekeeper.controller;


import cdw.springtraining.gatekeeper.service.BlackListServiceImpl;
import cdw.springtraining.gatekeeper.service.ResidentServiceImpl;
import cdw.springtraining.gatekeeper.model.BlackListRequest;
import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.VisitorSlotRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static cdw.springtraining.gatekeeper.constant.SuccessConstants.RESIDENT_CHECKED;
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
