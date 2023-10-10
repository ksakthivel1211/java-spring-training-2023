package cdw.springTraining.gatekeeper.dao;

import cdw.springTraining.gatekeeper.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author sakthivel
 * BlackListRepository class has the jpa methods of black list
 */
public interface BlackListRepository extends JpaRepository<BlackList,Integer> {
    Optional<BlackList> findByMail(String mail);
    Boolean existsByMail(String mail);
}
