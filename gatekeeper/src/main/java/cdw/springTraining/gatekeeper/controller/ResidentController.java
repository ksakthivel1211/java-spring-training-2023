package cdw.springTraining.gatekeeper.controller;

import cdw.springTraining.gatekeeper.model.BlackListRequest;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.VisitorSlotRequest;
import cdw.springTraining.gatekeeper.service.BlackListServiceImpl;
import cdw.springTraining.gatekeeper.service.ResidentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sakthivel
 * Resident controller has the endpoints of resident operations
 */
@RestController
public class ResidentController implements ResidentApi{
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
    public ResponseEntity<ControllerResponse> removeVisitorSlot(Integer requestId) {
        return ResponseEntity.status(HttpStatus.OK).body(residentServiceImpl.removeVisitorSlot(requestId));
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
     * @param userId The id related to the user (required)
     * @return - Controller response of success status
     */
    @Override
    public ResponseEntity<ControllerResponse> residentCheckingIn(Integer userId) {
        return ResponseEntity.status(HttpStatus.OK).body(residentServiceImpl.userChecked(userId,"in"));
    }

    /**
     * residentCheckingOut method is used to change the user checked status to "out"
     * @param userId The id related to the user (required)
     * @return - Controller response of success status
     */
    @Override
    public ResponseEntity<ControllerResponse> residentCheckingOut(Integer userId) {
        return ResponseEntity.status(HttpStatus.OK).body(residentServiceImpl.userChecked(userId,"out"));
    }
}
