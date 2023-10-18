package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.VisitorPassResponse;
import cdw.springTraining.gatekeeper.model.VisitorRequest;

/**
 * @author sakthivel
 * Visitor service has the functional methods of visitor operations
 */
public interface VisitorService {

    public ControllerResponse checkResident(VisitorRequest request);
    public VisitorPassResponse keyGen(VisitorRequest request);
}
