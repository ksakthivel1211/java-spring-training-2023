package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.UserResponse;

/**
 * @author sakthivel
 * Registration service has the functional methods of registration operations
 */
public interface RegistrationService {

    public ControllerResponse addUser(UserResponse user);
}
