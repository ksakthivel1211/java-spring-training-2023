package cdw.springtraining.gatekeeper.dao;

import cdw.springtraining.gatekeeper.entity.VisitorSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author sakthivel
 * VisitorSlotRepository class has the jpa methods of visitor slot
 */
public interface VisitorSlotRepository extends JpaRepository<VisitorSlot,Integer> {

    public Optional<List<VisitorSlot>> findByDate(LocalDate date);
    public Optional<VisitorSlot> findByMail(String mail);
    public List<VisitorSlot> findByDateAndMailAndInTimeLessThanAndOutTimeGreaterThan(LocalDate date, String mail, OffsetDateTime outTime,OffsetDateTime inTime);
}
