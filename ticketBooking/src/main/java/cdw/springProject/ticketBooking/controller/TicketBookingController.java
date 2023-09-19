package cdw.springProject.ticketBooking.controller;

import cdw.springProject.ticketBooking.entity.Shows;
import cdw.springProject.ticketBooking.entity.Tickets;
import cdw.springProject.ticketBooking.request.BookingRequest;
import cdw.springProject.ticketBooking.request.ShowRequest;
import cdw.springProject.ticketBooking.responseModel.ControllerResponse;
import cdw.springProject.ticketBooking.responseModel.TicketResponse;
import cdw.springProject.ticketBooking.service.TicketBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ControllerResponse> endUserBooking(@RequestBody BookingRequest userBookingRequest)
    {
        return ResponseEntity.status(HttpStatus.OK).body(ticketBookingService.endUserTicketBooking(userBookingRequest));
    }

    @GetMapping("/user/shows")
    public ResponseEntity<List<Shows>> getShow(@RequestBody ShowRequest showRequest)
    {
        return ResponseEntity.status(HttpStatus.OK).body(ticketBookingService.getShowDetails(showRequest));
    }

    @GetMapping("/user/tickets")
    public ResponseEntity<List<TicketResponse>> showTickets(@RequestParam int userId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(ticketBookingService.showTickets(userId));
    }

    @GetMapping("/user/ticket")
    public ResponseEntity<TicketResponse> showTicket(@RequestParam int ticketId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(ticketBookingService.getTicketDetails(ticketId));
    }

    @PutMapping("/user/tickets")
    public ResponseEntity<ControllerResponse> updateTicket(@RequestParam int ticketUpdateCount,int ticketId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(ticketBookingService.updateTicketCount(ticketUpdateCount,ticketId));
    }

    @PostMapping("/user/cancelRequest")
    public ResponseEntity<ControllerResponse> cancelRequest(@RequestParam int ticketId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(ticketBookingService.cancelRequest(ticketId));
    }
}
