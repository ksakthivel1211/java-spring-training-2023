package cdw.springTraining.gatekeeper.service;

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
public class AdminService {

    private RegistrationApprovalListRepository registrationApprovalListRepository;
    private BlackListRepository blackListRepository;
    private UserRepository userRepository;

    @Autowired
    public AdminService(RegistrationApprovalListRepository registrationApprovalListRepository,BlackListRepository blackListRepository,UserRepository userRepository)
    {
        this.registrationApprovalListRepository=registrationApprovalListRepository;
        this.blackListRepository=blackListRepository;
        this.userRepository=userRepository;
    }

    public List<RegistrationResponse> listAllRequest()
    {
        List<RegistrationResponse> registrationResponses = new ArrayList<>();
        registrationApprovalListRepository.findAll().stream().forEach(registrationApprovalList -> {
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

    public ControllerResponse grantUserRequest(int requestId)
    {
        RegistrationApprovalList request = registrationApprovalListRepository.findById(requestId).orElseThrow();
        User user = new User(request.getName(),request.getAge(),request.getGender(),request.getMail(),request.getPassword(),request.getRoleName());
        user.setUserId(0);
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

    public ControllerResponse rejectUserRequest(int requestId)
    {
        RegistrationApprovalList request = registrationApprovalListRepository.findById(requestId).orElseThrow();
        request.setStatus("rejected");
        registrationApprovalListRepository.save(request);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("Request has been rejected");
        return controllerResponse;
    }

    public ControllerResponse deleteUser(int userId)
    {
        User user = userRepository.findById(userId).orElseThrow();
        userRepository.delete(user);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("User has been deleted successfully");
        return controllerResponse;
    }

    public ControllerResponse updateUser(UserResponse user)
    {
        User tempUser = userRepository.findById(user.getId()).orElseThrow();

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
