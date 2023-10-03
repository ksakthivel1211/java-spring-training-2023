package cdw.springTraining.gatekeeper.controller;

import cdw.springTraining.gatekeeper.model.ApprovalResponse;
import cdw.springTraining.gatekeeper.model.BlackListRequest;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.SlotApprovalRequest;
import cdw.springTraining.gatekeeper.service.BlackListService;
import cdw.springTraining.gatekeeper.service.GateKeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class GateKeeperController implements GateKeeperApi{

    private GateKeeperService gateKeeperService;
    private BlackListService blackListService;

    @Autowired
    public GateKeeperController(GateKeeperService gateKeeperService,BlackListService blackListService)
    {
        this.gateKeeperService=gateKeeperService;
        this.blackListService=blackListService;
    }

    @Override
    public ResponseEntity<List<ApprovalResponse>> viewApprovalList(LocalDate date) {
        return ResponseEntity.status(HttpStatus.OK).body(gateKeeperService.getAllApproval(date));
    }

    @Override
    public ResponseEntity<ControllerResponse> gateKeeperBlackList(BlackListRequest blackListRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(blackListService.addToBlackList(blackListRequest));
    }

    @Override
    public ResponseEntity<ControllerResponse> slotApproval(SlotApprovalRequest slotApprovalRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(gateKeeperService.slotApproval(slotApprovalRequest));
    }
}
