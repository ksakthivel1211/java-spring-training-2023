package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.model.BlackListRequest;
import cdw.springTraining.gatekeeper.model.ControllerResponse;

public interface BlackListService {

    public ControllerResponse addToBlackList(BlackListRequest blackListRequest);

}
