package cdw.springTraining.gatekeeper.dao;

import cdw.springTraining.gatekeeper.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepository extends JpaRepository<BlackList,Integer> {
}
