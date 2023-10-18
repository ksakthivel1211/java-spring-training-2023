package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.UserResponse;
/**
 * @author sakthivel
 * Registration service has the functional methods of registration operations
 */
public interface RegistrationService {

    public ControllerResponse addUser(UserResponse user);
}
