package cdw.springtraining.gatekeeper.controller;


import cdw.springtraining.gatekeeper.entity.VisitorSlot;
import cdw.springtraining.gatekeeper.service.BlackListServiceImpl;
import cdw.springtraining.gatekeeper.service.GateKeeperServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import cdw.springtraining.gatekeeper.model.ApprovalResponse;
import cdw.springtraining.gatekeeper.model.BlackListRequest;
import org.springframework.http.HttpStatus;
import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.SlotApprovalRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static cdw.springtraining.gatekeeper.constant.SuccessConstants.VISITOR_SLOT_APPROVAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GateKeeperControllerTest {

    @InjectMocks
    private GateKeeperController gateKeeperController;

    @Mock
    private GateKeeperServiceImpl gateKeeperServiceImpl;

    @Mock
    private BlackListServiceImpl blackListServiceImpl;

    @Test
    public void testViewApprovalList()
    {
        List<ApprovalResponse> responses = new ArrayList<>();
        LocalDate date = LocalDate.parse("2021-01-07");
        when(gateKeeperServiceImpl.getAllApproval(date)).thenReturn(responses);
        assertEquals(responses,gateKeeperController.viewApprovalList(date).getBody());

    }

    @Test
    public void testGateKeeperBlackList(){
        BlackListRequest blackListRequest = new BlackListRequest();
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("The user has been black listed");
        when(blackListServiceImpl.addToBlackList(blackListRequest)).thenReturn(controllerResponse);
        assertEquals(controllerResponse,gateKeeperController.gateKeeperBlackList(blackListRequest).getBody());
    }

    @Test
    public void testSlotApproval()
    {
        VisitorSlot visitorSlot = new VisitorSlot();
        SlotApprovalRequest approvalRequest = new SlotApprovalRequest();
        approvalRequest.setApprovalStatus("approved");
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage(VISITOR_SLOT_APPROVAL + approvalRequest.getApprovalStatus());
        when(gateKeeperServiceImpl.slotApproval(approvalRequest)).thenReturn(controllerResponse);
        assertEquals(controllerResponse,gateKeeperController.slotApproval(approvalRequest).getBody());
    }
}
