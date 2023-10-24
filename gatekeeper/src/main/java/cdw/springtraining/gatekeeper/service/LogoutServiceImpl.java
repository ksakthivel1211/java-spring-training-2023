package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.dao.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author sakthivel
 * Logout service implementation has the method of logout operation
 */
@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutHandler {

    private final TokenRepository tokenRepository;

    /**
     * logout function changes the token expired values to true
     * @param request
     * @param response
     * @param authentication
     */
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
