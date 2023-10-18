package cdw.springTraining.gatekeeper.service;


import cdw.springTraining.gatekeeper.customException.GateKeepingCustomException;
import cdw.springTraining.gatekeeper.dao.UserRepository;
import cdw.springTraining.gatekeeper.dao.VisitorSlotRepository;
import cdw.springTraining.gatekeeper.entity.User;
import cdw.springTraining.gatekeeper.entity.VisitorSlot;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.VisitorPassResponse;
import cdw.springTraining.gatekeeper.model.VisitorRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

import static cdw.springTraining.gatekeeper.constant.ErrorConstants.USER_NOT_FOUND_BY_MAIL;
import static cdw.springTraining.gatekeeper.constant.SuccessConstants.RESIDENT_CHECKED;
import static cdw.springTraining.gatekeeper.constant.TimeConstant.VISITOR_PASSKEY_TIME;
import static com.auth0.jwt.algorithms.Algorithm.HMAC256;

/**
 * @author sakthivel
 * Visitor service implementation has the functional methods of visitor operations
 */
@Service
public class VisitorServiceImpl implements VisitorService{

    private VisitorSlotRepository visitorSlotRepository;
    private UserRepository userRepository;

    @Autowired
    public VisitorServiceImpl(VisitorSlotRepository visitorSlotRepository, UserRepository userRepository)
    {
        this.visitorSlotRepository=visitorSlotRepository;
        this.userRepository=userRepository;
    }

    @Value("${secret.key}")
    private String secretKey;

    /**
     * method is used to check if the resident has checked in or out by visitor
     * @param request - type object
     * @return - Controller response of success status
     */
    @Override
    public ControllerResponse checkResident(VisitorRequest request)
    {
            User user = userRepository.findByMail(request.getEmail()).orElseThrow(()-> new GateKeepingCustomException(USER_NOT_FOUND_BY_MAIL, HttpStatus.NOT_FOUND));
            String residentChecked = user.getChecked();
            ControllerResponse controllerResponse = new ControllerResponse();
            controllerResponse.setMessage(RESIDENT_CHECKED + residentChecked);
            return controllerResponse;
    }

    /**
     * keyGen method provides temporary pass for visitor
     * @param request - type string
     * @return - VisitorPassResponse with entryPass and visitor details
     */
    @Override
    public VisitorPassResponse keyGen(VisitorRequest request)
    {
//        throw new GateKeepingCustomException("hi");
        String secretKey = "123";
        VisitorSlot visitorSlot = visitorSlotRepository.findByMail(request.getEmail()).orElseThrow(()-> new GateKeepingCustomException(USER_NOT_FOUND_BY_MAIL,HttpStatus.NOT_FOUND));
        Algorithm algorithm= HMAC256(secretKey.getBytes());
        String pass =  JWT.create()
                .withSubject(visitorSlot.getMail())
                .withExpiresAt(new Date(System.currentTimeMillis()+VISITOR_PASSKEY_TIME))
                .sign(algorithm);
        VisitorPassResponse passResponse= new VisitorPassResponse();
        passResponse.setMail(visitorSlot.getMail());
        passResponse.setUserName(visitorSlot.getVisitorName());
        passResponse.setVisitorPass(pass);
        return passResponse;
    }





















}
