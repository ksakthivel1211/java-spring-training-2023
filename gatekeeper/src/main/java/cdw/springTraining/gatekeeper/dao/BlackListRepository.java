package cdw.springTraining.gatekeeper.dao;

import cdw.springTraining.gatekeeper.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlackListRepository extends JpaRepository<BlackList,Integer> {

    public Optional<BlackList> findByMail(String mail);
}
