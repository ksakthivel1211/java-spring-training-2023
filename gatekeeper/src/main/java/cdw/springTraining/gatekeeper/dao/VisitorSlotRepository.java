package cdw.springTraining.gatekeeper.dao;

import cdw.springTraining.gatekeeper.entity.VisitorSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VisitorSlotRepository extends JpaRepository<VisitorSlot,Integer> {

    public List<VisitorSlot> findByDate(LocalDate date);
}
