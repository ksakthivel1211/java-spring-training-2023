package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.dao.RegistrationApprovalListRepository;
import cdw.springTraining.gatekeeper.entity.RegistrationApprovalList;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.UserResponse;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private RegistrationApprovalListRepository registrationApprovalListRepository;

    @Autowired
    public RegistrationService(RegistrationApprovalListRepository registrationApprovalListRepository)
    {
        this.registrationApprovalListRepository=registrationApprovalListRepository;
    }

    public ControllerResponse addUser(UserResponse user)
    {
        if(user.getName().matches("resident|gateKeeper"))
        {
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
