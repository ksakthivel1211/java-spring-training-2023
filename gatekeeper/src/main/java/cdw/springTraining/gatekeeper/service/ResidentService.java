package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.VisitorSlotRequest;

/**
 * @author sakthivel
 * Resident service has the functional methods of resident operations
 */
public interface ResidentService {

    public ControllerResponse bookVisitorSlot(VisitorSlotRequest visitorSlotRequest);
    public ControllerResponse removeVisitorSlot(int slotId);
    public ControllerResponse userChecked(String checked);
}
