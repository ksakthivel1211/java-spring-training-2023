package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.VisitorSlotRequest;

public interface ResidentService {

    public ControllerResponse bookVisitorSlot(VisitorSlotRequest visitorSlotRequest);
    public ControllerResponse removeVisitorSlot(int slotId);
    public ControllerResponse userChecked(int userId, String checked);
}
