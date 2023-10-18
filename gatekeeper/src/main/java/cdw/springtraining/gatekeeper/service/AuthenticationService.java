package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.model.AuthenticationResponseVo;
/**
 * @author sakthivel
 * Authentication service has the authentication methods of login operations
 */
public interface AuthenticationService {

    public AuthenticationResponseVo authenticate(cdw.springtraining.gatekeeper.model.AuthenticationRequest authenticationRequest);
}
