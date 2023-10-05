package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.customException.GateKeepingCustomException;
import cdw.springTraining.gatekeeper.dao.BlackListRepository;
import cdw.springTraining.gatekeeper.dao.RegistrationApprovalListRepository;
import cdw.springTraining.gatekeeper.dao.UserRepository;
import cdw.springTraining.gatekeeper.entity.BlackList;
import cdw.springTraining.gatekeeper.entity.RegistrationApprovalList;
import cdw.springTraining.gatekeeper.entity.User;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.RegistrationResponse;
import cdw.springTraining.gatekeeper.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<RegistrationResponse> registrationResponses = new ArrayList<>();
        List<RegistrationApprovalList> approvalLists = registrationApprovalListRepository.findAll();
        if(approvalLists.isEmpty())
        {
            throw new GateKeepingCustomException("There are no registration request yet");
        }
        approvalLists.stream().forEach(registrationApprovalList -> {
            RegistrationResponse tempResponse = new RegistrationResponse();
            tempResponse.setAge(registrationApprovalList.getAge());
            tempResponse.setId(registrationApprovalList.getApproval_id());
            tempResponse.setStatus(registrationApprovalList.getStatus());
            tempResponse.setName(registrationApprovalList.getName());
            tempResponse.setGender(registrationApprovalList.getGender());
            tempResponse.setPassword(registrationApprovalList.getPassword());
            tempResponse.setMail(registrationApprovalList.getMail());
            tempResponse.setRoleName(registrationApprovalList.getRoleName());
            registrationResponses.add(tempResponse);
        });
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
        RegistrationApprovalList request = registrationApprovalListRepository.findById(requestId).orElseThrow(()-> new GateKeepingCustomException("Request with the given id is not found"));
        if(userRepository.findByMail(request.getMail()).isPresent())
        {
            throw new GateKeepingCustomException("User already approved");
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
        controllerResponse.setMessage("Request has been accepted");
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
        RegistrationApprovalList request = registrationApprovalListRepository.findById(requestId).orElseThrow(()-> new GateKeepingCustomException("Request with the given id is not found"));
        request.setStatus("rejected");
        registrationApprovalListRepository.save(request);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("Request has been rejected");
        return controllerResponse;
    }

    /**
     * deleteResident method gets the registration user id and passes to the delete user to delete the user
     * @param userId
     * @return - Controller response of success status
     */
    @Override
    public ControllerResponse deleteUser(int userId)
    {
        User user = userRepository.findById(userId).orElseThrow(()-> new GateKeepingCustomException("Request with the given id is not found"));
        userRepository.delete(user);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("User has been deleted successfully");
        return controllerResponse;
    }

    /**
     * updateResident method updates user details with the given data
     * @param user
     * @return - Controller response of success status
     */
    @Override
    public ControllerResponse updateUser(UserResponse user)
    {
        User tempUser = userRepository.findByMail(user.getMail()).orElseThrow(()-> new GateKeepingCustomException("user with the given user details is not found"));

        tempUser.setName(user.getName());
        tempUser.setAge(user.getAge());
        tempUser.setGender(user.getGender());
        tempUser.setMail(user.getMail());
        tempUser.setRoleName(user.getRoleName());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        tempUser.setPassword(encodedPassword);
        userRepository.save(tempUser);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("User details has been updated");
        return controllerResponse;
    }

}
