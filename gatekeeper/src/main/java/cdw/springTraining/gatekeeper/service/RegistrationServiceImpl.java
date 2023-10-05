package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.customException.GateKeepingCustomException;
import cdw.springTraining.gatekeeper.dao.BlackListRepository;
import cdw.springTraining.gatekeeper.dao.RegistrationApprovalListRepository;
import cdw.springTraining.gatekeeper.entity.RegistrationApprovalList;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
     * @param user
     * @return
     */
    @Override
    public ControllerResponse addUser(UserResponse user)
    {
        if(user.getRoleName().matches("resident|gateKeeper"))
        {
            blackListRepository.findByMail(user.getMail()).ifPresent(value-> {throw new GateKeepingCustomException("Unable to register : The user has been black listed");});
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());

            registrationApprovalListRepository.save(new RegistrationApprovalList("notApproved",user.getName(),user.getAge(),user.getGender(), user.getMail(), encodedPassword, user.getRoleName()));
            ControllerResponse controllerResponse = new ControllerResponse();
            controllerResponse.setMessage("User request has been successfully saved");
            return controllerResponse;
        }
        else {
            throw new RuntimeException("Only Residents and Gate keepers are allowed to register");
        }

    }
}
