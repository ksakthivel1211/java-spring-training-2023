package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.customException.GateKeepingCustomException;
import cdw.springTraining.gatekeeper.dao.VisitorSlotRepository;
import cdw.springTraining.gatekeeper.entity.User;
import cdw.springTraining.gatekeeper.entity.VisitorSlot;
import cdw.springTraining.gatekeeper.model.ApprovalResponse;
import cdw.springTraining.gatekeeper.model.ApprovalResponseUser;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.SlotApprovalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GateKeeperServiceImpl implements GateKeeperService{

    private VisitorSlotRepository visitorSlotRepository;

    @Autowired
    public GateKeeperServiceImpl(VisitorSlotRepository visitorSlotRepository)
    {
        this.visitorSlotRepository=visitorSlotRepository;
    }

    /**
     * slotApproval method used to approve or reject visitor request of resident
     * @param slotApprovalRequest
     * @return - Controller response of success status
     */
    @Override
    public ControllerResponse slotApproval(SlotApprovalRequest slotApprovalRequest)
    {
        if(!slotApprovalRequest.getApprovalStatus().matches("approved|rejected"))
        {
            throw new RuntimeException("Invalid approval status : Gate keeper can only approve or reject");
        }
        VisitorSlot visitorSlot = visitorSlotRepository.findById(slotApprovalRequest.getSlotId()).orElseThrow(()-> new GateKeepingCustomException("Visitor slot with the given request id is not found"));
        visitorSlot.setStatus(slotApprovalRequest.getApprovalStatus());
        visitorSlotRepository.save(visitorSlot);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("The visitor slot has been : "+ slotApprovalRequest.getApprovalStatus());
        return controllerResponse;
    }

    /**
     * getAllApproval method lists the approval request for gatekeeper
     * @param date
     * @return - list of ApprovalResponses
     */
    @Override
    public List<ApprovalResponse> getAllApproval(LocalDate date)
    {
        List<ApprovalResponse> approvalResponseList = new ArrayList<>();
        List<VisitorSlot> visitorSlots = visitorSlotRepository.findByDate(date).orElseThrow(()-> new GateKeepingCustomException("Visitor slot with the given date is not found"));
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
        return approvalResponseList;
    }

}
