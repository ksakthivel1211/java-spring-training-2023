package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.RegistrationResponse;
import cdw.springTraining.gatekeeper.model.UserResponse;

import java.util.List;

/**
 * @author sakthivel
 * Admin service has the functional methods of admin operations
 */
public interface AdminService {

    public List<RegistrationResponse> listAllRequest();
    public ControllerResponse grantUserRequest(int requestId);
    public ControllerResponse rejectUserRequest(int requestId);
    public ControllerResponse deleteUser(int requestId);
    public ControllerResponse updateUser(UserResponse user);

}
