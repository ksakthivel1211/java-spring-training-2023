package cdw.springProject.ticketBooking.config;

import cdw.springProject.ticketBooking.dao.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String authHeader = request.getHeader(AUTHORIZATION);
        String jwt;
        if(authHeader==null && !authHeader.startsWith("Bearer ")) {
            return;
        }

        jwt =authHeader.substring("Bearer ".length());
        var storedToken = tokenRepository.findByTokenName(jwt).orElse(null);

        if(storedToken != null)
        {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }

    }
}
