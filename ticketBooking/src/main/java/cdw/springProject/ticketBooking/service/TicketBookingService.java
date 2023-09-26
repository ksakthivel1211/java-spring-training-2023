package cdw.springProject.ticketBooking.service;

import cdw.springProject.ticketBooking.customException.BookingException;
import cdw.springProject.ticketBooking.dao.*;
import cdw.springProject.ticketBooking.entity.*;
import cdw.springProject.ticketBooking.request.BookingRequest;
import cdw.springProject.ticketBooking.request.ShowRequest;
import cdw.springProject.ticketBooking.responseModel.ControllerResponse;
import cdw.springProject.ticketBooking.responseModel.TicketResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class TicketBookingService {

    private TicketsRepository ticketsRepository;
    private BookingRequest userBookingRequest;
    private TheatreRepository theatreRepository;
    private ShowsRepository showsRepository;
    private UserRepository userRepository;
    private LocationRepository locationRepository;

    @Autowired
    public TicketBookingService(TicketsRepository theTicketsRepository, BookingRequest theUserBookingRequest, ShowsRepository theShowsRepository, TheatreRepository theTheatreRepository, UserRepository theUserRepository, LocationRepository theLocationRepository)
    {
        ticketsRepository=theTicketsRepository;
        userBookingRequest=theUserBookingRequest;
        theatreRepository=theTheatreRepository;
        showsRepository=theShowsRepository;
        userRepository=theUserRepository;
        locationRepository=theLocationRepository;
    }

    public ControllerResponse endUserTicketBooking(BookingRequest theUserBookingRequest, String userRole)
    {
        try
        {
            User user = userRepository.findByUserId(theUserBookingRequest.getUserId());
            Location location = locationRepository.findByLocationName(theUserBookingRequest.getLocation());
            Theatre theatre = theatreRepository.findByTheatreName(theUserBookingRequest.getTheatreName());
            Shows availableShow = showsRepository.findByShowSlotAndMovieNameAndDate(theUserBookingRequest.getShowSlot(), theUserBookingRequest.getMovieName(), theUserBookingRequest.getDate());
            int ticketCount = theUserBookingRequest.getTicketCount();

            if(userRole.equals("endUser")){
                if(location.getType().equals("NonPrime")) {
                    bookTickets(location,theatre,availableShow,user,ticketCount);
                }
                else {
                    throw new BookingException("Location not available for the user");
                }
            } else if (userRole.equals("businessUser")) {
                bookTickets(location,theatre,availableShow,user,ticketCount);
            }
            else {
                throw new BookingException("Invalid role");
            }


            return new ControllerResponse("Ticket has been booked successfully");
        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
    }

    private void bookTickets(Location location,Theatre theatre, Shows availableShow, User user, int ticketCount)
    {
        if (location.getIsActive()) {
            if (theatre != null) {
                if (availableShow != null) {
                    Tickets ticket = new Tickets();
                    ticket.setTicketId(0);
                    ticket.setUser(user);
                    ticket.setShows(availableShow);
                    ticket.setTicketCount(ticketCount);
                    ticket.setStatus("Booked");
                    ticketsRepository.save(ticket);
                    user.addTickets(ticket);
                    availableShow.addTickets(ticket);
                } else {
                    throw new BookingException("Show not available");
                }
            } else {
                throw new BookingException("Wrong theatre name");
            }
        } else {
            throw new BookingException("Location is inactive");
        }
    }

    public List<TicketResponse> showTickets(int userId)
    {
        try
        {
            User user = userRepository.findByUserId(userId);
            List<Tickets> tickets = ticketsRepository.findByUser(user);

            List<TicketResponse> ticketResponses = tickets.stream().map(ticketDetails-> new TicketResponse(ticketDetails.getTicketId(),ticketDetails.getShows(),ticketDetails.getUser(),ticketDetails.getTicketCount(),ticketDetails.getStatus())).collect(Collectors.toList());

            return ticketResponses;
        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
    }

    public TicketResponse getTicketDetails(int ticketId)
    {
        try
        {
            Tickets ticket = ticketsRepository.findById(ticketId).get();
            TicketResponse ticketResponse = new TicketResponse(ticket.getTicketId(),ticket.getShows(),ticket.getUser(),ticket.getTicketCount(),ticket.getStatus());
            return ticketResponse;
        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }

    }

    public ControllerResponse updateTicketCount(int ticketCount,int ticketId)
    {
        try
        {
            Tickets ticket = ticketsRepository.findById(ticketId).get();
            ticket.setTicketCount(ticket.getTicketCount()+ticketCount);
            ticketsRepository.save(ticket);
            return new ControllerResponse("Tickets count has been updated");
        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }

    }

    public ControllerResponse cancelRequest(int ticketId)
    {
        try
        {
            Tickets ticket = ticketsRepository.findById(ticketId).get();
            ticket.setStatus("CancelRequest");
            ticketsRepository.save(ticket);
            return new ControllerResponse("Ticket cancel request has been registered");
        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
    }

    public List<Shows> getShowDetails(ShowRequest showRequest)
    {
        try
        {
            Theatre theatre = theatreRepository.findByTheatreName(showRequest.getTheatreName());
            List<Shows> shows = new ArrayList<>();
            String movieName = showRequest.getMovieName();
            String showSlot = showRequest.getShowSlot();
            if(showRequest.getDate()!=null)
            {
                if(theatre!=null && showRequest.getShowSlot()!=null && showRequest.getMovieName()==null)
                {
                    shows = showsRepository.findByTheatreAndShowSlotAndDate(theatre,showRequest.getShowSlot(),showRequest.getDate());
                }

                else if (theatre!=null && showRequest.getMovieName()!=null && showRequest.getShowSlot()==null) {
                    shows = showsRepository.findByTheatreAndMovieNameAndDate(theatre,showRequest.getMovieName(),showRequest.getDate());
                }

                else if (theatre != null && showRequest.getMovieName()==null && showRequest.getShowSlot()==null) {
                    shows = showsRepository.findByTheatreAndDate(theatre,showRequest.getDate());
                }
            }
            else
            {
                throw new BookingException("Date field is missing . Date Field is not optional");
            }

            return shows;
        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
    }
}
