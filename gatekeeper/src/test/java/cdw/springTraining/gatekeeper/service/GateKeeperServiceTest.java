package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.dao.VisitorSlotRepository;
import cdw.springTraining.gatekeeper.entity.VisitorSlot;
import cdw.springTraining.gatekeeper.model.ApprovalResponse;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.SlotApprovalRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GateKeeperServiceTest {

    @InjectMocks
    private GateKeeperService gateKeeperService;

    @Mock
    private VisitorSlotRepository visitorSlotRepository;

    @Test
    public void testSlotApproval()
    {
        SlotApprovalRequest approvalRequest = new SlotApprovalRequest();
        approvalRequest.setApprovalStatus("approved");
        approvalRequest.setSlotId(1);
        String name = "sakthi";
        LocalDate date = LocalDate.parse("2021-01-07");
        OffsetDateTime inTime = OffsetDateTime.parse("2023-10-13T17:32:28Z");
        OffsetDateTime outTime = OffsetDateTime.parse("2023-10-13T17:32:28Z");

        VisitorSlot visitorSlot = new VisitorSlot(name,date,inTime,outTime);

        when(visitorSlotRepository.findById(approvalRequest.getSlotId())).thenReturn(Optional.of(visitorSlot));
        ControllerResponse response = new ControllerResponse();
        response.setMessage("The visitor slot has been : "+ approvalRequest.getApprovalStatus());

        assertEquals(response,gateKeeperService.slotApproval(approvalRequest));

    }

    @Test
    public void testGetAllApproval()
    {
        LocalDate date = LocalDate.parse("2021-01-07");
        List<ApprovalResponse> approvalResponses = new ArrayList<>();
        List<VisitorSlot> visitorSlots = new ArrayList<>();
        when(visitorSlotRepository.findByDate(date)).thenReturn(visitorSlots);
        assertEquals(approvalResponses,gateKeeperService.getAllApproval(date));
    }
}
