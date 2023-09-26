package cdw.springProject.ticketBooking.dao;

import cdw.springProject.ticketBooking.entity.Shows;
import cdw.springProject.ticketBooking.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShowsRepository extends JpaRepository<Shows, Integer> {

    public Shows findByShowSlotAndMovieNameAndDate(String showSlot, String MovieName, LocalDate date);
    public List<Shows> findByTheatreAndMovieNameAndDate(Theatre theatre ,String movieName,LocalDate date);
    public List<Shows> findByTheatreAndShowSlotAndDate(Theatre theatre,String showSlot, LocalDate date);
    public List<Shows> findByTheatreAndDate(Theatre theatre,LocalDate date);
    public List<Shows> findByTheatreAndDateAndShowSlot(Theatre theatre,LocalDate date, String showSlot);
}
