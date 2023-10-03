package cdw.springTraining.gatekeeper.controller;

import cdw.springTraining.gatekeeper.model.BlackListRequest;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.VisitorSlotRequest;
import cdw.springTraining.gatekeeper.service.BlackListService;
import cdw.springTraining.gatekeeper.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResidentController implements ResidentApi{

    private ResidentService residentService;
    private BlackListService blackListService;

    @Autowired
    public ResidentController(ResidentService residentService, BlackListService blackListService)
    {
        this.residentService=residentService;
        this.blackListService=blackListService;
    }
    @Override
    public ResponseEntity<ControllerResponse> bookVisitorSlot(VisitorSlotRequest visitorSlotRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(residentService.bookVisitorSlot(visitorSlotRequest));
    }

    @Override
    public ResponseEntity<ControllerResponse> removeVisitorSlot(Integer requestId) {
        return ResponseEntity.status(HttpStatus.OK).body(residentService.removeVisitorSlot(requestId));
    }

    @Override
    public ResponseEntity<ControllerResponse> residentBlackList(BlackListRequest blackListRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(blackListService.addToBlackList(blackListRequest));
    }
}
