package cdw.springtraining.gatekeeper.controller;

import cdw.springtraining.gatekeeper.service.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.controller.AuthenticationApi;
import cdw.springtraining.gatekeeper.model.AuthenticationRequest;
import cdw.springtraining.gatekeeper.model.AuthenticationResponseVo;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sakthivel
 * Authentication controller provides authentication service while logging in
 */
@RestController
public class AuthenticationController implements AuthenticationApi {

    private AuthenticationServiceImpl authenticationServiceImpl;

    @Autowired
    public AuthenticationController(AuthenticationServiceImpl authenticationServiceImpl)
    {
        this.authenticationServiceImpl = authenticationServiceImpl;
    }

    /**
     * Authenticate method validates the user details and provides access token
     * @param authenticationRequest user mail and password for authentication (required)
     * @return - AuthenticationResponse of token and user details
     */
    @Override
    public ResponseEntity<AuthenticationResponseVo> authenticate(AuthenticationRequest authenticationRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationServiceImpl.authenticate(authenticationRequest));
    }
}
