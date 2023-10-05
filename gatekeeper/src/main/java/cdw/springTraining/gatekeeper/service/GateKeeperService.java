package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.model.ApprovalResponse;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.SlotApprovalRequest;

import java.time.LocalDate;
import java.util.List;

public interface GateKeeperService {

    public ControllerResponse slotApproval(SlotApprovalRequest slotApprovalRequest);
    public List<ApprovalResponse> getAllApproval(LocalDate date);
}
