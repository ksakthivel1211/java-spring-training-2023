package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.dao.VisitorSlotRepository;
import cdw.springTraining.gatekeeper.entity.VisitorSlot;
import cdw.springTraining.gatekeeper.model.ApprovalResponse;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.SlotApprovalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GateKeeperService {

    private VisitorSlotRepository visitorSlotRepository;

    @Autowired
    public GateKeeperService(VisitorSlotRepository visitorSlotRepository)
    {
        this.visitorSlotRepository=visitorSlotRepository;
    }

    public ControllerResponse slotApproval(SlotApprovalRequest slotApprovalRequest)
    {
        if(!slotApprovalRequest.getApprovalStatus().matches("approved|rejected"))
        {
            throw new RuntimeException("Invalid approval status : Gate keeper can only approve or reject");
        }
        VisitorSlot visitorSlot = visitorSlotRepository.findById(slotApprovalRequest.getSlotId()).get();
        visitorSlot.setStatus(slotApprovalRequest.getApprovalStatus());
        visitorSlotRepository.save(visitorSlot);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("The visitor slot has been : "+ slotApprovalRequest.getApprovalStatus());
        return controllerResponse;
    }

    public List<ApprovalResponse> getAllApproval(LocalDate date)
    {
        List<ApprovalResponse> approvalResponseList = new ArrayList<>();
        List<VisitorSlot> visitorSlots = visitorSlotRepository.findByDate(date);
        visitorSlots.stream().forEach(slot ->{
            ApprovalResponse approvalResponse = new ApprovalResponse();
            approvalResponse.setSlotId(slot.getSlotId());
            approvalResponse.setDate(slot.getDate());
            approvalResponse.setUser(slot.getUserResponse());
            approvalResponse.setVisitorName(slot.getVisitorName());
            approvalResponse.setInTime(slot.getOffSetInTime());
            approvalResponse.setOutTime(slot.getOffSetOutTime());
            approvalResponseList.add(approvalResponse);
                }
        );
        return approvalResponseList;
    }

}
