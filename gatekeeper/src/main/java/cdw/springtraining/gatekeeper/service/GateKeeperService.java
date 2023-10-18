package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.SlotApprovalRequest;
import cdw.springtraining.gatekeeper.model.ApprovalResponse;
import java.time.LocalDate;
import java.util.List;

/**
 * @author sakthivel
 * Gate keeper service has the functional methods of Gate keeper operations
 */
public interface GateKeeperService {

    public ControllerResponse slotApproval(SlotApprovalRequest slotApprovalRequest);
    public List<ApprovalResponse> getAllApproval(LocalDate date);
}
