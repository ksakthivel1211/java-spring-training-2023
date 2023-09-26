package cdw.springProject.ticketBooking.controller;

import cdw.springProject.ticketBooking.request.RegistrationRequest;
import cdw.springProject.ticketBooking.responseModel.ControllerResponse;
import cdw.springProject.ticketBooking.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket-booking/registration")
public class RegistrationController {

    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService theRegistrationService)
    {
        registrationService=theRegistrationService;
    }

    @PostMapping("/")
    public ResponseEntity<ControllerResponse> registration(@RequestBody RegistrationRequest registrationRequest)
    {
        return ResponseEntity.status(HttpStatus.OK).body(registrationService.addUser(registrationRequest));
    }



}
