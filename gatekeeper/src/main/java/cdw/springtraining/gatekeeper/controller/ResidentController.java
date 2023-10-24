package cdw.springtraining.gatekeeper.controller;

import cdw.springtraining.gatekeeper.service.BlackListServiceImpl;
import cdw.springtraining.gatekeeper.service.ResidentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import cdw.springtraining.gatekeeper.model.BlackListRequest;
import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.VisitorSlotRequest;
import cdw.springtraining.gatekeeper.controller.ResidentApi;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sakthivel
 * Resident controller has the endpoints of resident operations
 */
@RestController
public class ResidentController implements ResidentApi {
    private ResidentServiceImpl residentServiceImpl;
    private BlackListServiceImpl blackListServiceImpl;

    @Autowired
    public ResidentController(ResidentServiceImpl residentServiceImpl, BlackListServiceImpl blackListServiceImpl)
    {
        this.residentServiceImpl = residentServiceImpl;
        this.blackListServiceImpl = blackListServiceImpl;
    }

    /**
     * bookVisitorSlot method is used to book a visitor slot by the resident
     * @param visitorSlotRequest Visitor slot object (required)
     * @return - Controller response of success status
     */
    @Override
    public ResponseEntity<ControllerResponse> bookVisitorSlot(VisitorSlotRequest visitorSlotRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(residentServiceImpl.bookVisitorSlot(visitorSlotRequest));
    }

    /**
     * removeVisitorSlot method is used to remove a visitor slot by the resident
     * @param requestId The id related to the slot (required)
     * @return - Controller response of success status
     */
    @Override
    public ResponseEntity<Void> removeVisitorSlot(Integer requestId) {
        residentServiceImpl.removeVisitorSlot(requestId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * residentBlacklist method black lists users
     * @param blackListRequest Black list object (required)
     * @return - Controller response of success status
     */
    @Override
    public ResponseEntity<ControllerResponse> residentBlackList(BlackListRequest blackListRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(blackListServiceImpl.addToBlackList(blackListRequest));
    }

    /**
     * residentCheckingIn method is used to change the user checked status to "in"
     * @return - Controller response of success status
     */
    @Override
    public ResponseEntity<ControllerResponse> residentCheckingIn() {
        return ResponseEntity.status(HttpStatus.OK).body(residentServiceImpl.userChecked("in"));
    }

    /**
     * residentCheckingOut method is used to change the user checked status to "out"
     * @return - Controller response of success status
     */
    @Override
    public ResponseEntity<ControllerResponse> residentCheckingOut() {
        return ResponseEntity.status(HttpStatus.OK).body(residentServiceImpl.userChecked("out"));
    }
}
