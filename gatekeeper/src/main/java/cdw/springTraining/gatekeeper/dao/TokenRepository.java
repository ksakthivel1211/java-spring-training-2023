package cdw.springTraining.gatekeeper.dao;

import cdw.springTraining.gatekeeper.entity.Token;
import cdw.springTraining.gatekeeper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Integer> {

    List<Token> findByExpiredAndRevokedAndUser(boolean expired, boolean revoked, User user);

    Optional<Token> findByTokenName(String token);
}
