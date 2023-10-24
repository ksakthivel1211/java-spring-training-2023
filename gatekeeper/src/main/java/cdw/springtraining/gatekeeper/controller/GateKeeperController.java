package cdw.springtraining.gatekeeper.controller;

import cdw.springtraining.gatekeeper.service.BlackListServiceImpl;
import cdw.springtraining.gatekeeper.service.GateKeeperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import cdw.springtraining.gatekeeper.model.ControllerResponse;
import org.springframework.http.ResponseEntity;
import cdw.springtraining.gatekeeper.model.ApprovalResponse;
import cdw.springtraining.gatekeeper.model.BlackListRequest;
import cdw.springtraining.gatekeeper.controller.GateKeeperApi;
import cdw.springtraining.gatekeeper.model.SlotApprovalRequest;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * @author sakthivel
 * Gate keeper controller provides the operations of gate keeper
 */
@RestController
public class GateKeeperController implements GateKeeperApi {

    private GateKeeperServiceImpl gateKeeperServiceImpl;
    private BlackListServiceImpl blackListServiceImpl;

    @Autowired
    public GateKeeperController(GateKeeperServiceImpl gateKeeperServiceImpl, BlackListServiceImpl blackListServiceImpl)
    {
        this.gateKeeperServiceImpl = gateKeeperServiceImpl;
        this.blackListServiceImpl = blackListServiceImpl;
    }

    /**
     * viewApprovalList method lists the approval request for gatekeepers
     * @param date Date input to get all the visitor approval of that day (required)
     * @return - list of ApprovalResponses
     */
    @Override
    public ResponseEntity<List<ApprovalResponse>> viewApprovalList(LocalDate date) {
        return ResponseEntity.status(HttpStatus.OK).body(gateKeeperServiceImpl.getAllApproval(date));
    }

    /**
     * gateKeeperBlacklist method black lists users
     * @param blackListRequest Black list object (required)
     * @return - Controller response of success status
     */
    @Override
    public ResponseEntity<ControllerResponse> gateKeeperBlackList(BlackListRequest blackListRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(blackListServiceImpl.addToBlackList(blackListRequest));
    }

    /**
     * slotApproval method used to approve or reject visitor request of resident
     * @param slotApprovalRequest Slot request object (required)
     * @return - Controller response of success status
     */
    @Override
    public ResponseEntity<ControllerResponse> slotApproval(SlotApprovalRequest slotApprovalRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(gateKeeperServiceImpl.slotApproval(slotApprovalRequest));
    }
}
