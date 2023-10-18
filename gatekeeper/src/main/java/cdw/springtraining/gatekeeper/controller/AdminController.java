package cdw.springtraining.gatekeeper.controller;

import cdw.springtraining.gatekeeper.service.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.ApprovalRequest;
import cdw.springtraining.gatekeeper.model.UserResponse;
import cdw.springtraining.gatekeeper.model.RegistrationResponse;
import cdw.springtraining.gatekeeper.controller.AdminApi;
import java.util.List;

/**
 * @author sakthivel
 * Admin controller has the endpoints of admin operations
 */
@RestController
public class AdminController implements AdminApi {

    private AdminServiceImpl adminServiceImpl;
    @Autowired
    public AdminController(AdminServiceImpl adminServiceImpl)
    {
        this.adminServiceImpl = adminServiceImpl;
    }

    /**
     * approveRequest method calls the grantUserRequest method to change the status of registration request
     * @param request - Its the id related to the registration request (required)
     * @return - Controller response of success status
     */
    @Override
    public ResponseEntity<ControllerResponse> approveRequest(ApprovalRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServiceImpl.grantUserRequest(request.getRequestId()));
    }

    /**
     * deleteResident method gets the registration user id and passes to the delete user to delete the user
     * @param userId Its the id related to the user(resident) (required)
     * @return - Controller response of success status
     */
    @Override
    public ResponseEntity<ControllerResponse> deleteResident(Integer userId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServiceImpl.deleteUser(userId));
    }

    /**
     * rejectRequest method gets requestId and passes it to rejectUserRequest method to change the request status
     * @param request - Its the id related to the registration request (required)
     * @return - Controller response of success status
     */
    @Override
    public ResponseEntity<ControllerResponse> rejectRequest(ApprovalRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServiceImpl.rejectUserRequest(request.getRequestId()));
    }

    /**
     * updateResident method updates user details with the given data
     * @param user User details (required)
     * @return - Controller response of success status
     */
    @Override
    public ResponseEntity<ControllerResponse> updateResident(UserResponse user) {
        return ResponseEntity.status(HttpStatus.OK).body(adminServiceImpl.updateUser(user));
    }

    /**
     * view request lists all the registration request made by the users
     * @return - List of registration response
     */
    @Override
    public ResponseEntity<List<RegistrationResponse>> viewRequest() {
        return ResponseEntity.status(HttpStatus.OK).body(adminServiceImpl.listAllRequest());
    }





}
