package cdw.springProject.ticketBooking.controller;

import cdw.springProject.ticketBooking.entity.Location;
import cdw.springProject.ticketBooking.entity.Role;
import cdw.springProject.ticketBooking.entity.Theatre;
import cdw.springProject.ticketBooking.request.TheatreRequest;
import cdw.springProject.ticketBooking.responseModel.ControllerResponse;
import cdw.springProject.ticketBooking.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket-booking/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService theAdminService)
    {
        adminService = theAdminService;
    }

    @PostMapping("/location")
    public ResponseEntity<Location> addLocation(@RequestBody Location theLocation)
    {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.addLocation(theLocation));
    }

    @PostMapping("/role")
    public ResponseEntity<Role> addRole(@RequestBody Role theRole)
    {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.addRole(theRole));
    }

    @PostMapping("/theatre")
    public ResponseEntity<ControllerResponse> addTheatre(@RequestBody TheatreRequest theatreRequest)
    {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.addTheatre(theatreRequest));
    }

}
