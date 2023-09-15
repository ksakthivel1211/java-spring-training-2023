package cdw.springProject.ticketBooking.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserBookingRequest {

    private int userId;
    private String movieName;
    private String showSlot;
    private LocalDate date;
    private String theatreName;
    private String location;


}
