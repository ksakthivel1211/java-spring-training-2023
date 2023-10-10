package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.VisitorPassResponse;

/**
 * @author sakthivel
 * Visitor service has the functional methods of visitor operations
 */
public interface VisitorService {

    public ControllerResponse checkResident(String residentMail);
    public VisitorPassResponse keyGen(String mail);
}
