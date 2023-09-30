package cdw.springTraining.gatekeeper.controller;

import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.UserResponse;
import cdw.springTraining.gatekeeper.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController implements RegistrationApi{

    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService)
    {
        this.registrationService=registrationService;
    }

    @Override
    public ResponseEntity<ControllerResponse> registrationRequest(UserResponse userResponse) {
        return ResponseEntity.status(HttpStatus.OK).body(registrationService.addUser(userResponse));
    }

}
