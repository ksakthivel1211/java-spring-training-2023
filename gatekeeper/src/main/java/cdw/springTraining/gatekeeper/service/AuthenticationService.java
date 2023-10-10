package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.entity.User;
import cdw.springTraining.gatekeeper.model.AuthenticationRequest;
import cdw.springTraining.gatekeeper.model.AuthenticationResponse;

/**
 * @author sakthivel
 * Authentication service has the authentication methods of login operations
 */
public interface AuthenticationService {

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
