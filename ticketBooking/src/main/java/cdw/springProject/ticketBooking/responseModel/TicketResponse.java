package cdw.springProject.ticketBooking.responseModel;

import cdw.springProject.ticketBooking.dao.TicketsRepository;
import cdw.springProject.ticketBooking.entity.Shows;
import cdw.springProject.ticketBooking.entity.Tickets;
import cdw.springProject.ticketBooking.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Data
@NoArgsConstructor
public class TicketResponse {

 private int ticketId;
 private int ticketCount;

 private String status;
 private Shows shows;
 private User user;


    public TicketResponse(int ticketId, Shows shows, User user, int theTicketCount, String status) {
        this.ticketId = ticketId;
        this.shows = shows;
        this.user = user;
        this.ticketCount=theTicketCount;
        this.status=status;
    }


}
