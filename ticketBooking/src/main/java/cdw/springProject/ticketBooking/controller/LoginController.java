package cdw.springProject.ticketBooking.controller;

import cdw.springProject.ticketBooking.entity.User;
import cdw.springProject.ticketBooking.request.LoginRequest;
import cdw.springProject.ticketBooking.service.LoginService;
import cdw.springProject.ticketBooking.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket-booking/login")
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService theLoginService)
    {
        loginService=theLoginService;
    }

    @PostMapping("/")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest)
    {
        return ResponseEntity.status(HttpStatus.OK).body(loginService.loginUser(loginRequest.getEmail(),loginRequest.getPassword()));
    }
}
