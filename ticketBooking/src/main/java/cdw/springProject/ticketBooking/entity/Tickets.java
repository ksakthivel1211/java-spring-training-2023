package cdw.springProject.ticketBooking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tickets")
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int ticketId;

    @Column(name = "cancel_request")
    private String status;

    @Column(name = "ticket_count")
    private int ticketCount;

    @JsonBackReference(value = "user-ticket")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "user")
    private User user;

    @JsonBackReference(value = "show-tickets")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "shows")
    private Shows shows;

}
