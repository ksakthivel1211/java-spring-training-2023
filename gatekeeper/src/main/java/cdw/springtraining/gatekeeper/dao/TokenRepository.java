package cdw.springtraining.gatekeeper.dao;

import cdw.springtraining.gatekeeper.entity.Token;
import cdw.springtraining.gatekeeper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author sakthivel
 * TokenRepository class has the jpa methods of token
 */
public interface TokenRepository extends JpaRepository<Token,Integer> {

    List<Token> findByExpiredAndRevokedAndUser(boolean expired, boolean revoked, User user);
    Boolean existsByExpiredAndRevoked(boolean expired, boolean revoked);
    Optional<Token> findByTokenName(String token);


}
