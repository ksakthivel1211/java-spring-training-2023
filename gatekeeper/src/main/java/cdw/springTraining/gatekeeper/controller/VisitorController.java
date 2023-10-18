package cdw.springTraining.gatekeeper.controller;

import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.VisitorPassResponse;
import cdw.springTraining.gatekeeper.service.VisitorServiceImpl;
import cdw.springTraining.gatekeeper.model.VisitorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sakthivel
 * Visitor controller has the endpoints of visitor operations
 */
@RestController
public class VisitorController implements cdw.springTraining.gatekeeper.controller.VisitorApi {

    private VisitorServiceImpl visitorService;

    @Autowired
    public VisitorController(VisitorServiceImpl visitorService)
    {
        this.visitorService=visitorService;
    }

    /**
     * getPass method provides temporary pass for visitor
     * @param request Visitor mail to check if visitor present and to generate pass for him (required)
     * @return - VisitorPassResponse with entryPass and visitor details
     */
    @Override
    public ResponseEntity<VisitorPassResponse> getPass(VisitorRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(visitorService.keyGen(request));
    }


    /**
     * residentCheck method is used to check if the resident has checked in or out by visitor
     * @param request Resident mail to check if resident has checked in or out (required)
     * @return - Controller response of success status
     */
    @Override
    public ResponseEntity<ControllerResponse> residentCheck(VisitorRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(visitorService.checkResident(request));
    }
}
