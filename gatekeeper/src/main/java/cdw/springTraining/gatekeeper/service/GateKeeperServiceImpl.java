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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static cdw.springTraining.gatekeeper.constant.ErrorConstants.*;
import static cdw.springTraining.gatekeeper.constant.SuccessConstants.VISITOR_SLOT_APPROVAL;

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
        visitorSlot.setStatus(slotApprovalRequest.getApprovalStatus());
        visitorSlotRepository.save(visitorSlot);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage(VISITOR_SLOT_APPROVAL + slotApprovalRequest.getApprovalStatus());
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

        List<VisitorSlot> visitorSlots = visitorSlotRepository.findByDate(date).orElseThrow(()-> new GateKeepingCustomException(SLOT_NOT_FOUND_BY_DATE,HttpStatus.NOT_FOUND));
        List<ApprovalResponse> approvalResponseList = visitorSlots.stream().map(slot ->{
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
            return approvalResponse;
                }
        ).collect(Collectors.toList());
        return approvalResponseList;
    }

}
