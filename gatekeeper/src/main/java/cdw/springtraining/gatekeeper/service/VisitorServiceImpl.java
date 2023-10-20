package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.constant.TimeConstant;
import cdw.springtraining.gatekeeper.custom.exception.GateKeepingCustomException;
import cdw.springtraining.gatekeeper.dao.UserRepository;
import cdw.springtraining.gatekeeper.dao.VisitorSlotRepository;
import cdw.springtraining.gatekeeper.entity.User;
import cdw.springtraining.gatekeeper.entity.VisitorSlot;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.VisitorRequest;
import cdw.springtraining.gatekeeper.model.VisitorPassResponse;
import static cdw.springtraining.gatekeeper.constant.ErrorConstants.USER_NOT_FOUND_BY_MAIL;
import static cdw.springtraining.gatekeeper.constant.ErrorConstants.VISITOR_SLOT_NOT_ACCEPTED_NO_VISITOR_PASS;
import static cdw.springtraining.gatekeeper.constant.SuccessConstants.RESIDENT_CHECKED;
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
        String secretKey = "123";
        VisitorSlot visitorSlot = visitorSlotRepository.findByMail(request.getEmail()).orElseThrow(()-> new GateKeepingCustomException(USER_NOT_FOUND_BY_MAIL,HttpStatus.NOT_FOUND));
        if(visitorSlot.getStatus().equals("rejected") || visitorSlot.getStatus().equals("notApproved"))
        {
            throw new GateKeepingCustomException(VISITOR_SLOT_NOT_ACCEPTED_NO_VISITOR_PASS + visitorSlot.getStatus(),HttpStatus.UNAUTHORIZED);
        }
        Algorithm algorithm= HMAC256(secretKey.getBytes());
        String pass =  JWT.create()
                .withSubject(visitorSlot.getMail())
                .withExpiresAt(new Date(System.currentTimeMillis()+ TimeConstant.VISITOR_PASSKEY_TIME))
                .sign(algorithm);
        VisitorPassResponse passResponse= new VisitorPassResponse();
        passResponse.setMail(visitorSlot.getMail());
        passResponse.setUserName(visitorSlot.getVisitorName());
        passResponse.setVisitorPass(pass);
        return passResponse;
    }





















}
