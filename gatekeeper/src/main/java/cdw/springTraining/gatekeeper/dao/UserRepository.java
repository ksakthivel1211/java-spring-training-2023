package cdw.springTraining.gatekeeper.dao;

import cdw.springTraining.gatekeeper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    public Optional<User> findByMail(String mail);
}
