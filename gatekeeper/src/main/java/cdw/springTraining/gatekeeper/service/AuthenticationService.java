package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.entity.User;
import cdw.springTraining.gatekeeper.model.AuthenticationRequest;
import cdw.springTraining.gatekeeper.model.AuthenticationResponse;

public interface AuthenticationService {

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
