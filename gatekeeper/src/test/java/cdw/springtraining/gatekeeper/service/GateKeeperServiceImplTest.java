package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.dao.VisitorSlotRepository;
import cdw.springtraining.gatekeeper.entity.User;
import cdw.springtraining.gatekeeper.entity.VisitorSlot;
import cdw.springtraining.gatekeeper.service.GateKeeperServiceImpl;
import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.SlotApprovalRequest;
import cdw.springtraining.gatekeeper.model.ApprovalResponse;
import cdw.springtraining.gatekeeper.model.ApprovalResponseUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GateKeeperServiceImplTest {

    @InjectMocks
    private GateKeeperServiceImpl gateKeeperServiceImpl;

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
        String mail = "sakthi@gmail.com";

        VisitorSlot visitorSlot = new VisitorSlot(name,mail,date,inTime,outTime);
        visitorSlot.setStatus("notApproved");

        when(visitorSlotRepository.findById(approvalRequest.getSlotId())).thenReturn(Optional.of(visitorSlot));
        ControllerResponse response = new ControllerResponse();
        response.setMessage("The visitor slot has been : "+ approvalRequest.getApprovalStatus());

        assertEquals(response, gateKeeperServiceImpl.slotApproval(approvalRequest));

    }

    @Test
    public void testGetAllApproval()
    {
        User userValue = new User("sam",21,"male","sam@gmail.com","abc","resident");
        OffsetDateTime inTime = OffsetDateTime.parse("2023-10-13T17:32:28Z");
        OffsetDateTime outTime = OffsetDateTime.parse("2023-10-13T17:32:28Z");
        LocalDate date = LocalDate.parse("2021-01-07");
        List<ApprovalResponse> approvalResponseList = new ArrayList<>();
        List<VisitorSlot> visitorSlots = new ArrayList<>();

        VisitorSlot visitorSlot = new VisitorSlot();
        visitorSlot.setSlotId(1);
        visitorSlot.setDate(date);
        visitorSlot.setVisitorName("sakthi");
        visitorSlot.setMail("sakthi@gmail.com");
        visitorSlot.setInTime(inTime);
        visitorSlot.setOutTime(outTime);
        visitorSlot.setUser(userValue);

        visitorSlots.add(visitorSlot);
        visitorSlots.stream().forEach(slot ->{
                    User user = slot.getUser();
                    ApprovalResponseUser userDetails = new ApprovalResponseUser();
                    userDetails.setName(user.getName());
                    userDetails.setMail(user.getMail());
                    ApprovalResponse approvalResponse = new ApprovalResponse();
                    approvalResponse.setSlotId(slot.getSlotId());
                    approvalResponse.setDate(slot.getDate());
                    approvalResponse.setUser(userDetails);
                    approvalResponse.setVisitorName(slot.getVisitorName());
                    approvalResponse.setInTime(slot.getOffSetInTime());
                    approvalResponse.setOutTime(slot.getOffSetOutTime());
                    approvalResponseList.add(approvalResponse);
                }
        );
        when(visitorSlotRepository.findByDate(date)).thenReturn(Optional.of(visitorSlots));
        assertEquals(approvalResponseList, gateKeeperServiceImpl.getAllApproval(date));
    }
}
