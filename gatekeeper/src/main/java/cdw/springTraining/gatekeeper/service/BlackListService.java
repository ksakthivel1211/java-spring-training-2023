package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.model.BlackListRequest;
import cdw.springTraining.gatekeeper.model.ControllerResponse;

/**
 * @author sakthivel
 * Black list service has the functional methods of black list operations
 */
public interface BlackListService {

    public ControllerResponse addToBlackList(BlackListRequest blackListRequest);

}
