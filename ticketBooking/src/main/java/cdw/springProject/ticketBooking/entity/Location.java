package cdw.springProject.ticketBooking.entity;

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
@Table(name="location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private int locationId;

    @Column(name="location_name")
    private String locationName;

    @Column(name = "state")
    private String state;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private Boolean isActive;

    @JsonManagedReference(value = "theatre-location")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    private List<Theatre> theatres;

    public Location(String theLocationName, String theState, String theType, Boolean active)
    {
        locationName=theLocationName;
        state=theState;
        type=theType;
        isActive=active;
    }

    public void addTheatre(Theatre theatre)
    {
        if(theatres==null)
        {
            theatres= new ArrayList<>();
        }
        theatres.add(theatre);
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", locationName='" + locationName + '\'' +
                ", state='" + state + '\'' +
                ", type='" + type + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
