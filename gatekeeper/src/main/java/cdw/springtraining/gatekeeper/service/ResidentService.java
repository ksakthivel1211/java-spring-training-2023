package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.VisitorSlotRequest;
/**
 * @author sakthivel
 * Resident service has the functional methods of resident operations
 */
public interface ResidentService {

    public ControllerResponse bookVisitorSlot(VisitorSlotRequest visitorSlotRequest);
    public ControllerResponse removeVisitorSlot(int slotId);
    public ControllerResponse userChecked(String checked);
}
