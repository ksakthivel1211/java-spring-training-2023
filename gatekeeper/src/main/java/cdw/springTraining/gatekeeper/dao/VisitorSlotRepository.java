package cdw.springTraining.gatekeeper.dao;

import cdw.springTraining.gatekeeper.entity.VisitorSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VisitorSlotRepository extends JpaRepository<VisitorSlot,Integer> {

    public Optional<List<VisitorSlot>> findByDate(LocalDate date);
    public Optional<VisitorSlot> findByMail(String mail);
}
