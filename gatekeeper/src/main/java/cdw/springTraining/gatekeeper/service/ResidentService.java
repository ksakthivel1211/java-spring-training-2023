package cdw.springTraining.gatekeeper.service;

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
public class ResidentService {

    private BlackListRepository blackListRepository;
    private VisitorSlotRepository visitorSlotRepository;
    private UserRepository userRepository;

    @Autowired
    public ResidentService(BlackListRepository blackListRepository,VisitorSlotRepository visitorSlotRepository,UserRepository userRepository)
    {
        this.blackListRepository=blackListRepository;
        this.visitorSlotRepository=visitorSlotRepository;
        this.userRepository=userRepository;
    }

    public ControllerResponse bookVisitorSlot(VisitorSlotRequest visitorSlotRequest)
    {
        User user = userRepository.findById(visitorSlotRequest.getUserId()).get();
        VisitorSlot visitorSlot = new VisitorSlot(visitorSlotRequest.getVisitorName(),visitorSlotRequest.getDate(),visitorSlotRequest.getInTime(),visitorSlotRequest.getOutTime());
        visitorSlot.setSlotId(0);
        visitorSlot.setStatus("notApproved");
        visitorSlot.setUser(user);
        user.addToVisitorSlot(visitorSlot);
        visitorSlotRepository.save(visitorSlot);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("Slot for the visitor has been booked");
        return controllerResponse;
    }

    public ControllerResponse removeVisitorSlot(int slotId)
    {
        VisitorSlot visitorSlot = visitorSlotRepository.findById(slotId).get();
        visitorSlotRepository.delete(visitorSlot);
        visitorSlot.getUser().getVisitorSlots().remove(visitorSlot);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("The visitor slot has been cancelled");
        return controllerResponse;
    }


}
