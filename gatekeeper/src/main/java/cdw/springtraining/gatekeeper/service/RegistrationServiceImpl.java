package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.constant.ErrorConstants;
import cdw.springtraining.gatekeeper.custom.exception.GateKeepingCustomException;
import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.UserResponse;
import cdw.springtraining.gatekeeper.dao.BlackListRepository;
import cdw.springtraining.gatekeeper.dao.RegistrationApprovalListRepository;
import cdw.springtraining.gatekeeper.dao.UserRepository;
import cdw.springtraining.gatekeeper.entity.RegistrationApprovalList;
import cdw.springtraining.gatekeeper.entity.User;
import cdw.springtraining.gatekeeper.utils.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static cdw.springtraining.gatekeeper.constant.SuccessConstants.USER_REGISTERED_SUCCESS;

/**
 * @author sakthivel
 * Registration service implementation has the functional methods of registration operations
 */
@Service
public class RegistrationServiceImpl implements RegistrationService{

    private RegistrationApprovalListRepository registrationApprovalListRepository;
    private BlackListRepository blackListRepository;
    private UserRepository userRepository;
    private AdminServiceImpl adminService;

    @Autowired
    public RegistrationServiceImpl(RegistrationApprovalListRepository registrationApprovalListRepository, BlackListRepository blackListRepository, UserRepository userRepository, AdminServiceImpl adminService)
    {
        this.registrationApprovalListRepository=registrationApprovalListRepository;
        this.blackListRepository = blackListRepository;
        this.userRepository = userRepository;
        this.adminService = adminService;
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

            blackListRepository.findByMail(user.getMail()).ifPresent(value-> {throw new GateKeepingCustomException(ErrorConstants.USER_BLACK_LISTED_INVALID_REGISTRATION, HttpStatus.ALREADY_REPORTED);});
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());

            String userName = UserInformation.getUserName();

            RegistrationApprovalList approvalList = registrationApprovalListRepository.save(new RegistrationApprovalList("notApproved",user.getName(),user.getAge(),user.getGender(), user.getMail(), encodedPassword, user.getRoleName()));

            User userDetails = userRepository.findByMail(userName).orElse(null);
            if(userDetails!=null)
            {
                if(userDetails.getRoleName().equals("admin")) {
                    return adminService.grantUserRequest(approvalList.getApproval_id());
                }
            }


            ControllerResponse controllerResponse = new ControllerResponse();
            controllerResponse.setMessage(USER_REGISTERED_SUCCESS);
            return controllerResponse;

        }
        else {
            throw new GateKeepingCustomException(ErrorConstants.INVALID_REGISTRATION_NAME,HttpStatus.UNAUTHORIZED);
        }

    }
}
