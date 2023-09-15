package cdw.springProject.ticketBooking.controller;

import cdw.springProject.ticketBooking.request.ShowRequest;
import cdw.springProject.ticketBooking.responseModel.ControllerResponse;
import cdw.springProject.ticketBooking.service.BusinessUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket-booking/business-user")
public class BusinessUserController {

    private BusinessUserService businessUserService;

    @Autowired
    public BusinessUserController(BusinessUserService theBusinessUserService)
    {
        businessUserService = theBusinessUserService;
    }

    @PostMapping("/add-show")
    public ResponseEntity<ControllerResponse> addShows(@RequestBody ShowRequest showRequest)
    {
        return ResponseEntity.status(HttpStatus.OK).body(businessUserService.addShows(showRequest));
    }

    @DeleteMapping("/delete-show")
    public ResponseEntity<ControllerResponse> deleteShows(@RequestParam int showId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(businessUserService.deleteShows(showId));
    }
}
