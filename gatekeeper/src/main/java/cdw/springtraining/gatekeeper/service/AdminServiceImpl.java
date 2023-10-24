package cdw.springtraining.gatekeeper.service;


import cdw.springtraining.gatekeeper.custom.exception.GateKeepingCustomException;
import cdw.springtraining.gatekeeper.dao.BlackListRepository;
import cdw.springtraining.gatekeeper.dao.RegistrationApprovalListRepository;
import cdw.springtraining.gatekeeper.dao.UserRepository;
import cdw.springtraining.gatekeeper.entity.BlackList;
import cdw.springtraining.gatekeeper.model.RegistrationResponse;
import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.entity.RegistrationApprovalList;
import cdw.springtraining.gatekeeper.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import cdw.springtraining.gatekeeper.model.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

import static cdw.springtraining.gatekeeper.constant.ErrorConstants.*;
import static cdw.springtraining.gatekeeper.constant.SuccessConstants.*;

/**
 * @author sakthivel
 * Admin service implementation has the functional methods of admin operations
 */
@Service
public class AdminServiceImpl implements AdminService{

    private RegistrationApprovalListRepository registrationApprovalListRepository;
    private BlackListRepository blackListRepository;
    private UserRepository userRepository;

    @Autowired
    public AdminServiceImpl(RegistrationApprovalListRepository registrationApprovalListRepository, BlackListRepository blackListRepository, UserRepository userRepository)
    {
        this.registrationApprovalListRepository=registrationApprovalListRepository;
        this.blackListRepository=blackListRepository;
        this.userRepository=userRepository;
    }

    /**
     * view request lists all the registration request made by the users
     * @return - List of registration response
     */
    @Override
    public List<RegistrationResponse> listAllRequest()
    {
        List<RegistrationApprovalList> approvalLists = registrationApprovalListRepository.findAll();
        if(approvalLists.isEmpty())
        {
            throw new GateKeepingCustomException(NO_REGISTRATION_REQUEST, HttpStatus.NO_CONTENT);
        }
        List<RegistrationResponse> registrationResponses = approvalLists.stream().map(registrationApprovalList -> {
            RegistrationResponse currentResponse = new RegistrationResponse();
            currentResponse.setAge(registrationApprovalList.getAge());
            currentResponse.setId(registrationApprovalList.getApproval_id());
            currentResponse.setStatus(registrationApprovalList.getStatus());
            currentResponse.setName(registrationApprovalList.getName());
            currentResponse.setGender(registrationApprovalList.getGender());
            currentResponse.setMail(registrationApprovalList.getMail());
            currentResponse.setRoleName(registrationApprovalList.getRoleName());
            return currentResponse;
        }).collect(Collectors.toList());
        return registrationResponses;
    }

    /**
     * grantUserRequest method used to change the status of registration request
     * @param requestId
     * @return - Controller response of success status
     */
    @Override
    public ControllerResponse grantUserRequest(int requestId)
    {
        RegistrationApprovalList request = registrationApprovalListRepository.findById(requestId).orElseThrow(()-> new GateKeepingCustomException(REQUEST_NOT_FOUND_BY_ID, HttpStatus.NOT_FOUND));
        if(userRepository.existsByMail(request.getMail()))
        {
            throw new GateKeepingCustomException(USER_ALREADY_APPROVED,HttpStatus.ALREADY_REPORTED);
        }
        User user = new User(request.getName(),request.getAge(),request.getGender(),request.getMail(),request.getPassword(),request.getRoleName());
        user.setUserId(0);
        user.setChecked("out");
        BlackList blackList = new BlackList();
        blackList.setBlackListId(0);
        blackListRepository.save(blackList);
        userRepository.save(user);
        request.setStatus("approved");
        registrationApprovalListRepository.save(request);

        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage(REQUEST_ACCEPTED);
        return controllerResponse;
    }

    /**
     * rejectRequest method gets requestId and passes it to rejectUserRequest method to change the request status
     * @param requestId
     * @return - Controller response of success status
     */
    @Override
    public ControllerResponse rejectUserRequest(int requestId)
    {
        RegistrationApprovalList request = registrationApprovalListRepository.findById(requestId).orElseThrow(()-> new GateKeepingCustomException(REQUEST_NOT_FOUND_BY_ID, HttpStatus.NOT_FOUND));
        request.setStatus("rejected");
        registrationApprovalListRepository.save(request);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage(REQUEST_REJECTED);
        return controllerResponse;
    }

    /**
     * deleteResident method gets the registration user id and passes to the delete user to delete the user
     * @param userId
     * @return - Controller response of success status
     */
    @Override
    public void deleteUser(int userId)
    {
        User user = userRepository.findById(userId).orElseThrow(()-> new GateKeepingCustomException(USER_NOT_FOUND_BY_ID,HttpStatus.NOT_FOUND));
        userRepository.delete(user);
    }

    /**
     * updateResident method updates user details with the given data
     * @param user
     * @return - Controller response of success status
     */
    @Override
    public void updateUser(UserResponse user)
    {
        User currentUser = userRepository.findByMail(user.getMail()).orElseThrow(()-> new GateKeepingCustomException(USER_NOT_FOUND_BY_MAIL,HttpStatus.NOT_FOUND));

        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setGender(user.getGender());
        currentUser.setMail(user.getMail());
        currentUser.setRoleName(user.getRoleName());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        currentUser.setPassword(encodedPassword);
        userRepository.save(currentUser);
    }

}
