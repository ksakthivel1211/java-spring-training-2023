package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.constant.ErrorConstants;
import cdw.springTraining.gatekeeper.constant.SuccessConstants;
import cdw.springTraining.gatekeeper.customException.GateKeepingCustomException;
import cdw.springTraining.gatekeeper.dao.BlackListRepository;
import cdw.springTraining.gatekeeper.dao.RegistrationApprovalListRepository;
import cdw.springTraining.gatekeeper.entity.RegistrationApprovalList;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static cdw.springTraining.gatekeeper.constant.ErrorConstants.INVALID_REGISTRATION_NAME;
import static cdw.springTraining.gatekeeper.constant.ErrorConstants.USER_BLACK_LISTED_INVALID_REGISTRATION;
import static cdw.springTraining.gatekeeper.constant.SuccessConstants.USER_REGISTERED_SUCCESS;

/**
 * @author sakthivel
 * Registration service implementation has the functional methods of registration operations
 */
@Service
public class RegistrationServiceImpl implements RegistrationService{

    private RegistrationApprovalListRepository registrationApprovalListRepository;
    private BlackListRepository blackListRepository;

    @Autowired
    public RegistrationServiceImpl(RegistrationApprovalListRepository registrationApprovalListRepository, BlackListRepository blackListRepository)
    {
        this.registrationApprovalListRepository=registrationApprovalListRepository;
        this.blackListRepository = blackListRepository;
    }

    /**
     * addUser method used to register request for resident and gate-keeper
     * @param user - type UserResponse
     * @return - Controller response with success message
     */
    @Override
    public ControllerResponse addUser(UserResponse user)
    {
        if(user.getRoleName().matches("resident|gateKeeper"))
        {
            blackListRepository.findByMail(user.getMail()).ifPresent(value-> {throw new GateKeepingCustomException(USER_BLACK_LISTED_INVALID_REGISTRATION, HttpStatus.ALREADY_REPORTED);});
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());

            registrationApprovalListRepository.save(new RegistrationApprovalList("notApproved",user.getName(),user.getAge(),user.getGender(), user.getMail(), encodedPassword, user.getRoleName()));
            ControllerResponse controllerResponse = new ControllerResponse();
            controllerResponse.setMessage(USER_REGISTERED_SUCCESS);
            return controllerResponse;
        }
        else {
            throw new GateKeepingCustomException(INVALID_REGISTRATION_NAME,HttpStatus.UNAUTHORIZED);
        }

    }
}
