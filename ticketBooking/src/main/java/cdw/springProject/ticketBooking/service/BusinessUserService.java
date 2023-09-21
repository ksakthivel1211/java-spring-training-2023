package cdw.springProject.ticketBooking.service;

import cdw.springProject.ticketBooking.customException.BookingException;
import cdw.springProject.ticketBooking.dao.ShowsRepository;
import cdw.springProject.ticketBooking.dao.TheatreRepository;
import cdw.springProject.ticketBooking.dao.TicketsRepository;
import cdw.springProject.ticketBooking.entity.RoleEntity;
import cdw.springProject.ticketBooking.entity.Shows;
import cdw.springProject.ticketBooking.entity.Theatre;
import cdw.springProject.ticketBooking.entity.Tickets;
import cdw.springProject.ticketBooking.request.BookingRequest;
import cdw.springProject.ticketBooking.request.ShowRequest;
import cdw.springProject.ticketBooking.responseModel.ControllerResponse;
import cdw.springProject.ticketBooking.responseModel.TicketResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class BusinessUserService {

    private ShowsRepository showsRepository;
    private ShowRequest showRequest;
    private TheatreRepository theatreRepository;
    private TicketsRepository ticketsRepository;
    private TicketBookingService ticketBookingService;

    @Autowired
    public BusinessUserService(ShowsRepository theShowsRepository, ShowRequest theShowRequest, TheatreRepository theTheatreRepository, TicketsRepository theTicketRepository,TicketBookingService ticketBookingService)
    {
        showsRepository=theShowsRepository;
        showRequest=theShowRequest;
        theatreRepository=theTheatreRepository;
        ticketsRepository=theTicketRepository;
        this.ticketBookingService=ticketBookingService;
    }

    public ControllerResponse addShows(ShowRequest theShowRequest)
    {
        try
        {
            Shows shows = showRequest.returnShow(theShowRequest);
            Theatre theatre = theatreRepository.findByTheatreName(theShowRequest.getTheatreName());
            if(showsRepository.findByTheatreAndDateAndShowSlot(theatre,theShowRequest.getDate(),theShowRequest.getShowSlot()) == null)
            {
                shows.setShowId(0);
                shows.setTheatre(theatre);
                showsRepository.save(shows);
                theatre.addShows(shows);
                return new ControllerResponse("New shows has been added to "+theShowRequest.getTheatreName()+" theatre");
            }
            else {
                throw new BookingException("Another show exists at that slot");
            }

        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }

    }

    public ControllerResponse deleteShows(int showId)
    {
        try {
            Shows show = showsRepository.findById(showId).get();
            showsRepository.delete(show);
            return new ControllerResponse("show has been deleted");
        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
    }

    public List<TicketResponse> getCancelRequest(int showId)
    {
        try {
            List<Tickets> tickets = showsRepository.findById(showId).get().getTickets();
            List<TicketResponse> ticketResponses = tickets.stream().filter(ticketsDetails -> ticketsDetails.getStatus().equals("CancelRequest"))
                    .map(tickets1 -> {
                        return new TicketResponse(tickets1.getTicketId(),tickets1.getShows(),tickets1.getUser(),tickets1.getTicketCount(),tickets1.getStatus());
                    }).filter(Objects::nonNull).collect(Collectors.toList());
            return ticketResponses;
        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }

    }

    public ControllerResponse cancelTicket(int ticketId)
    {
        try {
            Tickets ticket = ticketsRepository.findById(ticketId).get();
            if(ticket.getStatus().equals("CancelRequest"))
            {
                ticketsRepository.delete(ticket);
                return new ControllerResponse("Ticket Id :"+ ticket.getTicketId() + " has been successfully cancelled");
            }
            else
            {
                throw new BookingException("Ticket id does not have cancellation request");
            }
        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
    }

    public ControllerResponse businessUserBooking(BookingRequest bookingRequest)
    {
        try {
            return ticketBookingService.endUserTicketBooking(bookingRequest, String.valueOf(RoleEntity.businessUser));
        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
    }

    public List<TicketResponse> showBooking(int userId)
    {
        return ticketBookingService.showTickets(userId);
    }

}
