package cdw.springtraining.gatekeeper.controller;

import cdw.springtraining.gatekeeper.service.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import cdw.springtraining.gatekeeper.model.ControllerResponse;
import org.springframework.http.ResponseEntity;
import cdw.springtraining.gatekeeper.model.UserResponse;
import cdw.springtraining.gatekeeper.controller.RegistrationApi;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sakthivel
 * Registration controller has the endpoints for user registration operation
 */
@RestController
public class RegistrationController implements RegistrationApi {

    private RegistrationServiceImpl registrationServiceImpl;

    @Autowired
    public RegistrationController(RegistrationServiceImpl registrationServiceImpl)
    {
        this.registrationServiceImpl = registrationServiceImpl;
    }

    /**
     * registrationRequest method used to register request for resident and gate-keeper
     * @param userResponse User details (required)
     * @return - Controller response of success status
     */
    @Override
    public ResponseEntity<ControllerResponse> registrationRequest(UserResponse userResponse) {
        return ResponseEntity.status(HttpStatus.OK).body(registrationServiceImpl.addUser(userResponse));
    }

}
