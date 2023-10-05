package cdw.springTraining.gatekeeper.controller;

import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.VisitorPassResponse;
import cdw.springTraining.gatekeeper.service.VisitorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sakthivel
 * Visitor controller has the endpoints of visitor operations
 */
@RestController
public class VisitorController implements VisitorApi{

    private VisitorServiceImpl visitorService;

    @Autowired
    public VisitorController(VisitorServiceImpl visitorService)
    {
        this.visitorService=visitorService;
    }

    /**
     * getPass method provides temporary pass for visitor
     * @param mail Visitor mail to check if visitor present and to generate pass for him (required)
     * @return - VisitorPassResponse with entryPass and visitor details
     */
    @Override
    public ResponseEntity<VisitorPassResponse> getPass(String mail) {
        return ResponseEntity.status(HttpStatus.OK).body(visitorService.keyGen(mail));
    }

    /**
     * residentCheck method is used to check if the resident has checked in or out by visitor
     * @param residentMail Resident mail to check if resident has checked in or out (required)
     * @return - Controller response of success status
     */
    @Override
    public ResponseEntity<ControllerResponse> residentCheck(String residentMail) {
        return ResponseEntity.status(HttpStatus.OK).body(visitorService.checkResident(residentMail));
    }
}
