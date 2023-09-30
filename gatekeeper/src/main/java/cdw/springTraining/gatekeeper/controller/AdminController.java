package cdw.springTraining.gatekeeper.controller;

import cdw.springTraining.gatekeeper.entity.RegistrationApprovalList;
import cdw.springTraining.gatekeeper.entity.User;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.RegistrationResponse;
import cdw.springTraining.gatekeeper.model.UserResponse;
import cdw.springTraining.gatekeeper.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@RestController
public class AdminController implements AdminApi{

    private AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService)
    {
        this.adminService=adminService;
    }


    @Override
    public ResponseEntity<ControllerResponse> approveRequest(Integer requestId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.grantUserRequest(requestId));
    }

    @Override
    public ResponseEntity<ControllerResponse> deleteResident(Integer requestId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.deleteUser(requestId));
    }

    @Override
    public ResponseEntity<ControllerResponse> rejectRequest(Integer requestId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.rejectUserRequest(requestId));
    }

    @Override
    public ResponseEntity<ControllerResponse> updateResident(UserResponse user) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateUser(user));
    }

    @Override
    public ResponseEntity<List<RegistrationResponse>> viewRequest() {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.listAllRequest());
    }



}
