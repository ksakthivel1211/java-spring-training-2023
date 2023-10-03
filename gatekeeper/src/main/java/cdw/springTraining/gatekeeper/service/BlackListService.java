package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.dao.BlackListRepository;
import cdw.springTraining.gatekeeper.dao.UserRepository;
import cdw.springTraining.gatekeeper.entity.BlackList;
import cdw.springTraining.gatekeeper.entity.User;
import cdw.springTraining.gatekeeper.model.BlackListRequest;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;

@Service
public class BlackListService {

    private BlackListRepository blackListRepository;
    private UserRepository userRepository;

    @Autowired
    public BlackListService(BlackListRepository blackListRepository,UserRepository userRepository)
    {
        this.blackListRepository=blackListRepository;
        this.userRepository=userRepository;
    }

    public ControllerResponse addToBlackList(BlackListRequest blackListRequest){

        if(blackListRepository.findByMail(blackListRequest.getMail()).isPresent())
        {
            throw new RuntimeException("User has already been black listed");
        }
        BlackList blackList = new BlackList(blackListRequest.getName(),blackListRequest.getMail(), blackListRequest.getUserId());
        blackList.setBlackListId(0);
        blackListRepository.save(blackList);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("The user has been black listed");
        return controllerResponse;
    }
}
