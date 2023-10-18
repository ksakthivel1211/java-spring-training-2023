package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.BlackListRequest;
/**
 * @author sakthivel
 * Black list service has the functional methods of black list operations
 */
public interface BlackListService {

    public ControllerResponse addToBlackList(BlackListRequest blackListRequest);

}
