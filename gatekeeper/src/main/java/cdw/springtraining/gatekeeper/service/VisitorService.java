package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.VisitorRequest;
import cdw.springtraining.gatekeeper.model.VisitorPassResponse;
/**
 * @author sakthivel
 * Visitor service has the functional methods of visitor operations
 */
public interface VisitorService {

    public ControllerResponse checkResident(VisitorRequest request);
    public VisitorPassResponse keyGen(VisitorRequest request);
}
