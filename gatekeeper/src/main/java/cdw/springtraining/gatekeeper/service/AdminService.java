package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.RegistrationResponse;
import cdw.springtraining.gatekeeper.model.UserResponse;
import java.util.List;

/**
 * @author sakthivel
 * Admin service has the functional methods of admin operations
 */
public interface AdminService {

    public List<RegistrationResponse> listAllRequest();
    public ControllerResponse grantUserRequest(int requestId);
    public ControllerResponse rejectUserRequest(int requestId);
    public void deleteUser(int requestId);
    public void updateUser(UserResponse user);

}
