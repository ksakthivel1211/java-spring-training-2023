package cdw.springProject.ticketBooking.dao;

import cdw.springProject.ticketBooking.entity.Tickets;
import cdw.springProject.ticketBooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketsRepository extends JpaRepository<Tickets, Integer> {

    public List<Tickets> findByUser(User user);
}
