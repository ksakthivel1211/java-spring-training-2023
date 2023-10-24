package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.custom.exception.GateKeepingCustomException;
import cdw.springtraining.gatekeeper.dao.UserRepository;
import cdw.springtraining.gatekeeper.dao.VisitorSlotRepository;
import cdw.springtraining.gatekeeper.entity.User;
import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.VisitorSlotRequest;
import cdw.springtraining.gatekeeper.entity.VisitorSlot;
import cdw.springtraining.gatekeeper.utils.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Collection;

import static cdw.springtraining.gatekeeper.constant.ErrorConstants.*;
import static cdw.springtraining.gatekeeper.constant.SuccessConstants.*;

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
        String email = UserInformation.getUserName();
        User user = userRepository.findByMail(email).orElseThrow(()-> new GateKeepingCustomException(USER_NOT_FOUND_BY_ID, HttpStatus.NOT_FOUND));
        if(!user.getRoleName().equals("resident")){
            throw new GateKeepingCustomException(ONLY_RESIDENT_BOOKING,HttpStatus.NOT_FOUND);
        }
        if(!visitorSlotRepository.findByDateAndMailAndInTimeLessThanAndOutTimeGreaterThan(visitorSlotRequest.getDate(),visitorSlotRequest.getMail(),visitorSlotRequest.getOutTime(),visitorSlotRequest.getInTime()).isEmpty())
        {
            throw new GateKeepingCustomException(VISITOR_ALREADY_BOOKED,HttpStatus.BAD_REQUEST);
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
    public void removeVisitorSlot(int slotId)
    {
        VisitorSlot visitorSlot = visitorSlotRepository.findById(slotId).orElseThrow(()-> new GateKeepingCustomException(USER_NOT_FOUND_BY_ID,HttpStatus.NOT_FOUND));

        if(visitorSlot.getStatus().equals("rejected"))
        {
            throw new GateKeepingCustomException(VISITOR_SLOT_REJECTED,HttpStatus.UNAUTHORIZED);
        }
        if(visitorSlot.getInTime().isBefore(OffsetDateTime.now()))
        {
            throw new GateKeepingCustomException(VISITOR_SLOT_DELETE_AFTER_TIME,HttpStatus.FORBIDDEN);
        }
        visitorSlotRepository.delete(visitorSlot);
    }

    /**
     * residentCheckingOut method is used to change the user checked status to "out" or "in"
     * @param checked - type string
     * @return - Controller response of success status
     */
    @Override
    public ControllerResponse userChecked(String checked)
    {
        if(checked.matches("in|out"))
        {
            Collection<? extends GrantedAuthority> roles = UserInformation.getRoles();

            roles.stream().forEach(role-> {
                if(!role.getAuthority().equals("resident"))
                {
                    throw new GateKeepingCustomException(ONLY_RESIDENT_CHECKING,HttpStatus.UNAUTHORIZED);
                }
            });
            User user = userRepository.findByMail(UserInformation.getUserName()).orElseThrow(()-> new GateKeepingCustomException(USER_NOT_FOUND_BY_ID,HttpStatus.NOT_FOUND));
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
