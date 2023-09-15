package cdw.springProject.ticketBooking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="shows")
public class Shows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_id")
    private int showId;

    @Column(name="show_slot")
    private String showSlot;

    @Column(name="movie_name")
    private String movieName;

    @Column(name="date")
    private LocalDate date;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY , mappedBy = "shows")
    private List<Tickets> tickets;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre")
    private Theatre theatre;

    public Shows(String theShowSlot,String theMovieName, LocalDate theDate)
    {
        showSlot=theShowSlot;
        movieName=theMovieName;
        date=theDate;
    }

    public void addTickets(Tickets theTickets)
    {
        if(tickets==null)
        {
            tickets= new ArrayList<>();
        }
        tickets.add(theTickets);
    }

    @Override
    public String toString() {
        return "Shows{" +
                "showId=" + showId +
                ", showSlot='" + showSlot + '\'' +
                ", movieName='" + movieName + '\'' +
                ", date=" + date +
                '}';
    }
}
