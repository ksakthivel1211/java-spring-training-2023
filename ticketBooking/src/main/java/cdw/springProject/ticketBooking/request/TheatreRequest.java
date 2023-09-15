package cdw.springProject.ticketBooking.request;

import cdw.springProject.ticketBooking.entity.Theatre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class TheatreRequest {

    private String theatreName;
    private int capacity;
    private String location;

    public Theatre returnTheatre(TheatreRequest theatreRequest)
    {
        return new Theatre(theatreRequest.theatreName,theatreRequest.capacity);
    }
}
