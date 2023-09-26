package cdw.springProject.ticketBooking.dao;

import cdw.springProject.ticketBooking.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepository extends JpaRepository<Theatre, Integer> {

    public Theatre findByTheatreName(String theatreName);
}
