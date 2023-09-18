package cdw.springProject.ticketBooking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="theatre")
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theatre_id")
    private int theatreId;

    @Column(name="theatre_name")
    private String theatreName;

    @Column(name="capacity")
    private int capacity;

    @JsonBackReference(value = "theatre-location")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location")
    private Location location;

    @JsonManagedReference(value = "theatre-show")
    @OneToMany(fetch = FetchType.LAZY , mappedBy = "theatre")
    private List<Shows> shows;

    public Theatre(String theTheatreName,int theatreCapacity)
    {
        theatreName=theTheatreName;
        capacity=theatreCapacity;
    }

    public void addShows(Shows theShows)
    {
        if(shows==null)
        {
            shows= new ArrayList<>();
        }
        shows.add(theShows);
    }

    @Override
    public String toString() {
        return "Theatre{" +
                "theatreId=" + theatreId +
                ", theatreName='" + theatreName + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
