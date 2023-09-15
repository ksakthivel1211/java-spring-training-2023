package cdw.springProject.ticketBooking.service;

import cdw.springProject.ticketBooking.dao.*;
import cdw.springProject.ticketBooking.entity.*;
import cdw.springProject.ticketBooking.request.UserBookingRequest;
import cdw.springProject.ticketBooking.responseModel.ControllerResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
public class TicketBookingService {

    private TicketsRepository ticketsRepository;
    private UserBookingRequest userBookingRequest;
    private TheatreRepository theatreRepository;
    private ShowsRepository showsRepository;
    private UserRepository userRepository;
    private LocationRepository locationRepository;

    @Autowired
    public TicketBookingService(TicketsRepository theTicketsRepository, UserBookingRequest theUserBookingRequest, ShowsRepository theShowsRepository, TheatreRepository theTheatreRepository, UserRepository theUserRepository, LocationRepository theLocationRepository)
    {
        ticketsRepository=theTicketsRepository;
        userBookingRequest=theUserBookingRequest;
        theatreRepository=theTheatreRepository;
        showsRepository=theShowsRepository;
        userRepository=theUserRepository;
        locationRepository=theLocationRepository;
    }

    public ControllerResponse endUserTicketBooking(UserBookingRequest theUserBookingRequest)
    {
        try
        {
            User user = userRepository.findByUserId(theUserBookingRequest.getUserId());
            if(user!=null)
            {
                Location location = locationRepository.findByLocationName(theUserBookingRequest.getLocation());
                if(location.getIsActive())
                {
                    Theatre theatre = theatreRepository.findByTheatreName(theUserBookingRequest.getTheatreName());
                    if (theatre != null) {
                        Shows availableShow = showsRepository.findByShowSlotAndMovieNameAndDate(theUserBookingRequest.getShowSlot(),theUserBookingRequest.getMovieName(),theUserBookingRequest.getDate());
                        if(availableShow != null)
                        {
                            Tickets ticket = new Tickets();
                            ticket.setTicketId(0);
                            ticket.setUser(user);
                            ticket.setShows(availableShow);
                            ticketsRepository.save(ticket);
                            user.addTickets(ticket);
                            availableShow.addTickets(ticket);
                        }
                    }
                    else {
                        throw new RuntimeException();
                    }
                }
                else
                {
                    throw new RuntimeException("not allowed");
                }
            }
            else {
                throw new RuntimeException("user not found");
            }

            return new ControllerResponse("Ticket has been booked successfully");
        }
        catch (Exception exception)
        {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
