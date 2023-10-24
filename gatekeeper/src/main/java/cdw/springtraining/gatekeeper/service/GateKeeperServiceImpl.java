package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.custom.exception.GateKeepingCustomException;
import cdw.springtraining.gatekeeper.dao.VisitorSlotRepository;
import cdw.springtraining.gatekeeper.entity.User;
import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.SlotApprovalRequest;
import cdw.springtraining.gatekeeper.model.ApprovalResponse;
import cdw.springtraining.gatekeeper.model.ApprovalResponseUser;
import cdw.springtraining.gatekeeper.entity.VisitorSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static cdw.springtraining.gatekeeper.constant.ErrorConstants.*;
import static cdw.springtraining.gatekeeper.constant.SuccessConstants.VISITOR_SLOT_APPROVAL;

/**
 * @author sakthivel
 * Gate keeper service implementation has the functional methods of Gate keeper operations
 */
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
            throw new GateKeepingCustomException(INVALID_APPROVAL_STATUS, HttpStatus.BAD_REQUEST);
        }
        VisitorSlot visitorSlot = visitorSlotRepository.findById(slotApprovalRequest.getSlotId()).orElseThrow(()-> new GateKeepingCustomException(SLOT_NOT_FOUND_BY_ID,HttpStatus.NOT_FOUND));
        if(visitorSlot.getStatus().matches("approved|rejected"))
        {
            throw new GateKeepingCustomException(VISITOR_APPROVAL_ALREADY_DONE+visitorSlot.getStatus(),HttpStatus.ALREADY_REPORTED);
        }
        visitorSlot.setStatus(slotApprovalRequest.getApprovalStatus());
        visitorSlotRepository.save(visitorSlot);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage(VISITOR_SLOT_APPROVAL + slotApprovalRequest.getApprovalStatus());
        return controllerResponse;
    }

    /**
     * getAllApproval method lists the approval request for gatekeepers
     * @param date
     * @return - list of ApprovalResponses
     */
    @Override
    public List<ApprovalResponse> getAllApproval(LocalDate date)
    {

        List<VisitorSlot> visitorSlots = visitorSlotRepository.findByDate(date).orElseThrow(()-> new GateKeepingCustomException(SLOT_NOT_FOUND_BY_DATE,HttpStatus.NOT_FOUND));
        List<ApprovalResponse> approvalResponseList = visitorSlots.stream().map(slot ->{
            User user = slot.getUser();
            ApprovalResponseUser userDetails = new ApprovalResponseUser();
            userDetails.setName(user.getName());
            userDetails.setMail(user.getMail());
            ApprovalResponse approvalResponse = new ApprovalResponse();
            approvalResponse.setSlotId(slot.getSlotId());
            approvalResponse.setStatus(slot.getStatus());
            approvalResponse.setDate(slot.getDate());
            approvalResponse.setUser(userDetails);
            approvalResponse.setVisitorName(slot.getVisitorName());
            approvalResponse.setInTime(slot.getOffSetInTime());
            approvalResponse.setOutTime(slot.getOffSetOutTime());
            return approvalResponse;
                }
        ).collect(Collectors.toList());
        return approvalResponseList;
    }

}
