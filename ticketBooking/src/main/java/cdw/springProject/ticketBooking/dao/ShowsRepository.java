package cdw.springProject.ticketBooking.dao;

import cdw.springProject.ticketBooking.entity.Shows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShowsRepository extends JpaRepository<Shows, Integer> {

    public Shows findByShowSlotAndMovieNameAndDate(String showSlot, String MovieName, LocalDate date);
    public List<Shows> findByMovieNameAndDate(String movieName,LocalDate date);

    public List<Shows> findByShowSlotAndDate(String showSlot, LocalDate date);
}
