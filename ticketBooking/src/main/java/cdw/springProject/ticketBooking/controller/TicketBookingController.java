package cdw.springProject.ticketBooking.controller;

import cdw.springProject.ticketBooking.request.UserBookingRequest;
import cdw.springProject.ticketBooking.responseModel.ControllerResponse;
import cdw.springProject.ticketBooking.service.TicketBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket-booking")
public class TicketBookingController {

    private TicketBookingService ticketBookingService;

    @Autowired
    public TicketBookingController(TicketBookingService theTicketBookingService)
    {
        ticketBookingService=theTicketBookingService;
    }

    @PostMapping("/user")
    public ResponseEntity<ControllerResponse> endUserBooking(@RequestBody UserBookingRequest userBookingRequest)
    {
        return ResponseEntity.status(HttpStatus.OK).body(ticketBookingService.endUserTicketBooking(userBookingRequest));
    }
}
