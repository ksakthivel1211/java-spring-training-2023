package cdw.springProject.ticketBooking.service;

import cdw.springProject.ticketBooking.customException.BookingException;
import cdw.springProject.ticketBooking.dao.LocationRepository;
import cdw.springProject.ticketBooking.dao.RoleRepository;
import cdw.springProject.ticketBooking.dao.TheatreRepository;
import cdw.springProject.ticketBooking.entity.Location;
import cdw.springProject.ticketBooking.entity.Role;
import cdw.springProject.ticketBooking.entity.Theatre;
import cdw.springProject.ticketBooking.request.TheatreRequest;
import cdw.springProject.ticketBooking.responseModel.ControllerResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class AdminService {

    private LocationRepository locationRepository;
    private RoleRepository roleRepository;
    private TheatreRepository theatreRepository;
    private TheatreRequest theatreRequest;

    @Autowired
    public AdminService(LocationRepository theLocationRepository,RoleRepository theRoleRepository, TheatreRepository theTheatreRepository, TheatreRequest theTheatreRequest)
    {
        locationRepository=theLocationRepository;
        roleRepository=theRoleRepository;
        theatreRepository=theTheatreRepository;
        theatreRequest=theTheatreRequest;
    }

    public Location addLocation(Location location)
    {
        try{
            if(locationRepository.findNyLocationNameAndState(location.getLocationName(), location.getState()) == null)
            {
                location.setLocationId(0);
                Location dbLocation = locationRepository.save(location);
                return dbLocation;
            }

            else {
                throw new BookingException("Location already exists");
            }
        }
        catch(Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
    }

    public Role addRole(Role role)
    {
        try{
            if(roleRepository.findByRoleName(role.getRoleName()) == null)
            {
                role.setRoleId(0);
                Role dbRole = roleRepository.save(role);
                return dbRole;
            }
            else {
                throw new BookingException("Role already exists");
            }

        }
        catch(Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
    }

    public ControllerResponse deleteTheatre(String theatreName)
    {
        try
        {
            Theatre theatre = theatreRepository.findByTheatreName(theatreName);
            if(theatre!=null)
            {
                theatreRepository.delete(theatre);
                return new ControllerResponse("theatre has been deleted");
            }
            else
            {
                throw new BookingException("Theatre not found");
            }
        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
    }

    public ControllerResponse addTheatre(TheatreRequest theTheatreRequest)
    {
        try{
            Theatre theatre = theatreRequest.returnTheatre(theTheatreRequest);
            Location location = locationRepository.findByLocationName(theTheatreRequest.getLocation());
            theatre.setTheatreId(0);
            theatre.setLocation(location);
            theatreRepository.save(theatre);
            location.addTheatre(theatre);
            return new ControllerResponse("Theatre has been added");
        }
        catch(Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
    }

}
