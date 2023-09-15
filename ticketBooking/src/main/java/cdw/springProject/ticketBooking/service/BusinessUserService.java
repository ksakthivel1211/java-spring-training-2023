package cdw.springProject.ticketBooking.service;

import cdw.springProject.ticketBooking.dao.ShowsRepository;
import cdw.springProject.ticketBooking.dao.TheatreRepository;
import cdw.springProject.ticketBooking.entity.Shows;
import cdw.springProject.ticketBooking.entity.Theatre;
import cdw.springProject.ticketBooking.request.ShowRequest;
import cdw.springProject.ticketBooking.responseModel.ControllerResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class BusinessUserService {

    private ShowsRepository showsRepository;
    private ShowRequest showRequest;
    private TheatreRepository theatreRepository;

    @Autowired
    public BusinessUserService(ShowsRepository theShowsRepository, ShowRequest theShowRequest, TheatreRepository theTheatreRepository)
    {
        showsRepository=theShowsRepository;
        showRequest=theShowRequest;
        theatreRepository=theTheatreRepository;
    }

    public ControllerResponse addShows(ShowRequest theShowRequest)
    {
        Shows shows = showRequest.returnShow(theShowRequest);
        Theatre theatre = theatreRepository.findByTheatreName(theShowRequest.getTheatreName());
        shows.setShowId(0);
        shows.setTheatre(theatre);
        showsRepository.save(shows);
        theatre.addShows(shows);
        return new ControllerResponse("New shows has been added to "+theShowRequest.getTheatreName()+" theatre");
    }

    public ControllerResponse deleteShows(int showId)
    {
        Shows show = showsRepository.findById(showId).orElseThrow();
        showsRepository.delete(show);
        return new ControllerResponse("show has been deleted");
    }
}
