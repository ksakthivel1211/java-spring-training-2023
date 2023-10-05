package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.UserResponse;

public interface RegistrationService {

    public ControllerResponse addUser(UserResponse user);
}
