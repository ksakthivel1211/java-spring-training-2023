package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.customException.GateKeepingCustomException;
import cdw.springTraining.gatekeeper.dao.BlackListRepository;
import cdw.springTraining.gatekeeper.dao.UserRepository;
import cdw.springTraining.gatekeeper.dao.VisitorSlotRepository;
import cdw.springTraining.gatekeeper.entity.User;
import cdw.springTraining.gatekeeper.entity.VisitorSlot;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.VisitorSlotRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidentServiceImpl implements ResidentService{

    private BlackListRepository blackListRepository;
    private VisitorSlotRepository visitorSlotRepository;
    private UserRepository userRepository;

    @Autowired
    public ResidentServiceImpl(BlackListRepository blackListRepository, VisitorSlotRepository visitorSlotRepository, UserRepository userRepository)
    {
        this.blackListRepository=blackListRepository;
        this.visitorSlotRepository=visitorSlotRepository;
        this.userRepository=userRepository;
    }

    /**
     * bookVisitorSlot method is used to book a visitor slot by the resident
     * @param visitorSlotRequest
     * @return - Controller response of success status
     */
    @Override
    public ControllerResponse bookVisitorSlot(VisitorSlotRequest visitorSlotRequest)
    {
        User user = userRepository.findById(visitorSlotRequest.getUserId()).orElseThrow(()-> new GateKeepingCustomException("User with the given id is not found"));
        if(!user.getRoleName().equals("resident")){
            throw new GateKeepingCustomException("only resident will be able to book visitor slot");
        }
        VisitorSlot visitorSlot = new VisitorSlot(visitorSlotRequest.getVisitorName(),visitorSlotRequest.getMail(),visitorSlotRequest.getDate(),visitorSlotRequest.getInTime(),visitorSlotRequest.getOutTime());
        visitorSlot.setSlotId(0);
        visitorSlot.setStatus("notApproved");
        visitorSlot.setUser(user);
        user.addToVisitorSlot(visitorSlot);
        visitorSlotRepository.save(visitorSlot);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("Slot for the visitor has been booked");
        return controllerResponse;
    }

    /**
     * removeVisitorSlot method is used to remove a visitor slot by the resident
     * @param slotId
     * @return - Controller response of success status
     */
    @Override
    public ControllerResponse removeVisitorSlot(int slotId)
    {
        VisitorSlot visitorSlot = visitorSlotRepository.findById(slotId).orElseThrow(()-> new GateKeepingCustomException("Visitor with the given slot id is not found"));
        visitorSlotRepository.delete(visitorSlot);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("The visitor slot has been cancelled");
        return controllerResponse;
    }

    /**
     * residentCheckingOut method is used to change the user checked status to "out" or "in"
     * @param userId
     * @param checked
     * @return - Controller response of success status
     */
    @Override
    public ControllerResponse userChecked(int userId, String checked)
    {
        if(checked.matches("in|out"))
        {
            User user = userRepository.findById(userId).orElseThrow(()-> new GateKeepingCustomException("User with the given user id is not found"));
            user.setChecked(checked);
            userRepository.save(user);
            ControllerResponse controllerResponse = new ControllerResponse();
            controllerResponse.setMessage("User has been checked"+checked);
            return controllerResponse;
        }
        else {
            throw new GateKeepingCustomException("Invalid checked variable");
        }
    }




}
