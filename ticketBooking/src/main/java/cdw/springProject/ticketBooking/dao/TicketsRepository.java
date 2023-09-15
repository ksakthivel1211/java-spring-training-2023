package cdw.springProject.ticketBooking.dao;

import cdw.springProject.ticketBooking.entity.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketsRepository extends JpaRepository<Tickets, Integer> {
}
