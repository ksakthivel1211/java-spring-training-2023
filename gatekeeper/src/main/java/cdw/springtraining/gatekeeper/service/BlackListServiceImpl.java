package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.custom.exception.GateKeepingCustomException;
import cdw.springtraining.gatekeeper.dao.BlackListRepository;
import cdw.springtraining.gatekeeper.dao.UserRepository;
import cdw.springtraining.gatekeeper.entity.BlackList;
import cdw.springtraining.gatekeeper.entity.User;
import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.BlackListRequest;
import cdw.springtraining.gatekeeper.utils.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static cdw.springtraining.gatekeeper.constant.ErrorConstants.*;
import static cdw.springtraining.gatekeeper.constant.SuccessConstants.USER_BLACK_LISTED;

/**
 * @author sakthivel
 * Black list service implementation has the functional methods of black list operations
 */
@Service
public class BlackListServiceImpl implements BlackListService{

    private BlackListRepository blackListRepository;
    private UserRepository userRepository;


    @Autowired
    public BlackListServiceImpl(BlackListRepository blackListRepository, UserRepository userRepository)
    {
        this.blackListRepository=blackListRepository;
        this.userRepository = userRepository;
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

        Collection<? extends GrantedAuthority> roles = UserInformation.getRoles();
        roles.stream().forEach(role-> {
            if(role.getAuthority().equals("resident"))
            {
                userRepository.findByMail(blackListRequest.getMail()).ifPresent(value-> {throw new GateKeepingCustomException(RESIDENT_BLACK_LIST_ONLY_VISITOR, HttpStatus.BAD_REQUEST);});
            } else if (role.getAuthority().equals("admin")) {
                User blackListUser = userRepository.findByMail(blackListRequest.getMail()).orElseThrow(()->new GateKeepingCustomException(USER_NOT_FOUND_BY_MAIL, HttpStatus.NOT_FOUND));
                if(!blackListUser.getRoleName().equals("gateKeeper"))
                {
                    throw new GateKeepingCustomException(ADMIN_BLACK_LIST_ONLY_VISITOR, HttpStatus.BAD_REQUEST);
                }
            }
            else {
                throw new GateKeepingCustomException(ONLY_ADMIN_RESIDENT_BLACK_LIST, HttpStatus.UNAUTHORIZED);
            }
        });
        User user = userRepository.findByMail(UserInformation.getUserName()).orElseThrow(()-> new GateKeepingCustomException(USER_NOT_FOUND_BY_MAIL,HttpStatus.NOT_FOUND));

        BlackList blackList = new BlackList(blackListRequest.getName(),blackListRequest.getMail(), user.getUserId());
        blackList.setBlackListId(0);
        blackListRepository.save(blackList);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage(USER_BLACK_LISTED);
        return controllerResponse;
    }


}
