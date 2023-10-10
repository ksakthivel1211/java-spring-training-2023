package cdw.springTraining.gatekeeper.dao;

import cdw.springTraining.gatekeeper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author sakthivel
 * UserRepository class has the jpa methods of user
 */
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByMail(String mail);
    Optional<User> findByRoleName(String name);
    Boolean existsByMail(String mail);

}
