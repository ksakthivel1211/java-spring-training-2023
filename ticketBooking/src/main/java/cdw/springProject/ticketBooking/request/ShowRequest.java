package cdw.springProject.ticketBooking.request;

import cdw.springProject.ticketBooking.entity.Shows;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class ShowRequest {

    private String showSlot;
    private String movieName;
    private LocalDate date;
    private String theatreName;

    public Shows returnShow(ShowRequest showRequest)
    {
        return new Shows(showRequest.showSlot,showRequest.movieName,showRequest.date);
    }
}
