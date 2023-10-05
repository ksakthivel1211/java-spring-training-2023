package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.VisitorPassResponse;

public interface VisitorService {

    public ControllerResponse checkResident(String residentMail);
    public VisitorPassResponse keyGen(String mail);
}
