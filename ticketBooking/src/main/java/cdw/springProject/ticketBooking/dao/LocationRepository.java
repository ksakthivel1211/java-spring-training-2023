package cdw.springProject.ticketBooking.dao;

import cdw.springProject.ticketBooking.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location , Integer> {

    public Location findByLocationName(String locationName);
}
