package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.constant.ErrorConstants;
import cdw.springTraining.gatekeeper.constant.SuccessConstants;
import cdw.springTraining.gatekeeper.customException.GateKeepingCustomException;
import cdw.springTraining.gatekeeper.dao.BlackListRepository;
import cdw.springTraining.gatekeeper.dao.UserRepository;
import cdw.springTraining.gatekeeper.entity.BlackList;
import cdw.springTraining.gatekeeper.model.BlackListRequest;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static cdw.springTraining.gatekeeper.constant.ErrorConstants.USER_ALREADY_BLACK_LISTED;
import static cdw.springTraining.gatekeeper.constant.SuccessConstants.USER_BLACK_LISTED;

/**
 * @author sakthivel
 * Black list service implementation has the functional methods of black list operations
 */
@Service
public class BlackListServiceImpl implements BlackListService{

    private BlackListRepository blackListRepository;


    @Autowired
    public BlackListServiceImpl(BlackListRepository blackListRepository)
    {
        this.blackListRepository=blackListRepository;
    }

    /**
     * addToBlacklist method black lists users
     * @param blackListRequest
     * @return - Controller response of success status
     */
    @Override
    public ControllerResponse addToBlackList(BlackListRequest blackListRequest){

        if(blackListRepository.existsByMail(blackListRequest.getMail()))
        {
            throw new GateKeepingCustomException(USER_ALREADY_BLACK_LISTED, HttpStatus.NO_CONTENT);
        }
        BlackList blackList = new BlackList(blackListRequest.getName(),blackListRequest.getMail(), blackListRequest.getUserId());
        blackList.setBlackListId(0);
        blackListRepository.save(blackList);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage(USER_BLACK_LISTED);
        return controllerResponse;
    }
}
