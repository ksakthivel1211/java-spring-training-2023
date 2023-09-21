package cdw.springProject.ticketBooking.dao;

import cdw.springProject.ticketBooking.entity.User;
import cdw.springProject.ticketBooking.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Integer> {

    List<Token> findByExpiredAndRevokedAndUser(boolean expired, boolean revoked, User user);

    Optional<Token> findByTokenName(String token);
}
