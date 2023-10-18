package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.constant.ErrorConstants;
import cdw.springTraining.gatekeeper.constant.SuccessConstants;
import cdw.springTraining.gatekeeper.customException.GateKeepingCustomException;
import cdw.springTraining.gatekeeper.dao.BlackListRepository;
import cdw.springTraining.gatekeeper.dao.UserRepository;
import cdw.springTraining.gatekeeper.dao.VisitorSlotRepository;
import cdw.springTraining.gatekeeper.entity.User;
import cdw.springTraining.gatekeeper.entity.VisitorSlot;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.VisitorSlotRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static cdw.springTraining.gatekeeper.constant.ErrorConstants.*;
import static cdw.springTraining.gatekeeper.constant.SuccessConstants.*;

/**
 * @author sakthivel
 * Resident service implementation has the functional methods of resident operations
 */
@Service
public class ResidentServiceImpl implements ResidentService{

    private VisitorSlotRepository visitorSlotRepository;
    private UserRepository userRepository;

    @Autowired
    public ResidentServiceImpl(VisitorSlotRepository visitorSlotRepository, UserRepository userRepository)
    {
        this.visitorSlotRepository=visitorSlotRepository;
        this.userRepository=userRepository;
    }

    /**
     * bookVisitorSlot method is used to book a visitor slot by the resident
     * @param visitorSlotRequest - type VisitorSlotRequest
     * @return - Controller response of success status
     */
    @Override
    public ControllerResponse bookVisitorSlot(VisitorSlotRequest visitorSlotRequest)
    {
        User user = userRepository.findById(visitorSlotRequest.getUserId()).orElseThrow(()-> new GateKeepingCustomException(USER_NOT_FOUND_BY_ID, HttpStatus.NOT_FOUND));
        if(!user.getRoleName().equals("resident")){
            throw new GateKeepingCustomException(ONLY_RESIDENT_BOOKING,HttpStatus.NOT_FOUND);
        }
        if(visitorSlotRequest.getInTime().isAfter(visitorSlotRequest.getOutTime()))
        {
            throw new GateKeepingCustomException(IN_TIME_OUT_TIME_CONTRADICTION,HttpStatus.BAD_REQUEST);
        }
        VisitorSlot visitorSlot = new VisitorSlot(visitorSlotRequest.getVisitorName(),visitorSlotRequest.getMail(),visitorSlotRequest.getDate(),visitorSlotRequest.getInTime(),visitorSlotRequest.getOutTime());
        visitorSlot.setSlotId(0);
        visitorSlot.setStatus("notApproved");
        visitorSlot.setUser(user);
        user.addToVisitorSlot(visitorSlot);
        visitorSlotRepository.save(visitorSlot);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage(VISITOR_SLOT_BOOKED);
        return controllerResponse;
    }

    /**
     * removeVisitorSlot method is used to remove a visitor slot by the resident
     * @param slotId - type int
     * @return - Controller response of success status
     */
    @Override
    public ControllerResponse removeVisitorSlot(int slotId)
    {
        VisitorSlot visitorSlot = visitorSlotRepository.findById(slotId).orElseThrow(()-> new GateKeepingCustomException(USER_NOT_FOUND_BY_ID,HttpStatus.NOT_FOUND));
        visitorSlotRepository.delete(visitorSlot);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage(VISITOR_SLOT_CANCELLED);
        return controllerResponse;
    }

    /**
     * residentCheckingOut method is used to change the user checked status to "out" or "in"
     * @param checked - type string
     * @return - Controller response of success status
     */
    @Override
    public ControllerResponse userChecked(String checked)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(checked.matches("in|out"))
        {
            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
            roles.stream().forEach(role-> {
                if(!role.getAuthority().equals("resident"))
                {
                    throw new GateKeepingCustomException(ONLY_RESIDENT_CHECKING,HttpStatus.UNAUTHORIZED);
                }
            });
            User user = userRepository.findByMail(authentication.getName()).orElseThrow(()-> new GateKeepingCustomException(USER_NOT_FOUND_BY_ID,HttpStatus.NOT_FOUND));
            user.setChecked(checked);
            userRepository.save(user);
            ControllerResponse controllerResponse = new ControllerResponse();
            controllerResponse.setMessage(RESIDENT_CHECKED+checked);
            return controllerResponse;
        }
        else {
            throw new GateKeepingCustomException(INVALID_CHECKED_VARIABLE,HttpStatus.BAD_REQUEST);
        }
    }




}
