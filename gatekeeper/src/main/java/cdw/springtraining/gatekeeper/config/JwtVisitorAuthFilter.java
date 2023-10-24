package cdw.springtraining.gatekeeper.config;

import cdw.springtraining.gatekeeper.custom.exception.GateKeepingCustomException;
import cdw.springtraining.gatekeeper.dao.VisitorSlotRepository;
import cdw.springtraining.gatekeeper.service.JwtServiceImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.swagger.v3.oas.models.PathItem;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author sakthivel
 * JwtAuthenticationFilter is used to validate the Jwt token of visitor
 */
@Component
@RequiredArgsConstructor
public class JwtVisitorAuthFilter extends OncePerRequestFilter {

    @Autowired
    private final VisitorSlotRepository visitorSlotRepository;

    @Autowired
    private final UserDetailsService userDetailsService;

    @Autowired
    JwtServiceImpl jwtServiceImpl;

    private final RequestMatcher matcher= new AntPathRequestMatcher("/gate-keeping/visitor/resident-check", PathItem.HttpMethod.POST.name());

    @Value("${secret.key}")
    private String secretKey;

    /**
     * doFilterInternal method validates the jwt token for visitor
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(AUTHORIZATION);
        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            try{
                String token = authHeader.substring("Bearer ".length());
                Algorithm algorithm=Algorithm.HMAC256((secretKey.getBytes()));
                JWTVerifier verifier= JWT.require(algorithm).build();
                DecodedJWT decodedJWT=verifier.verify(token);
                String username=decodedJWT.getSubject();
                visitorSlotRepository.findByMail(username).get();

            } catch (Exception e) {
                throw new GateKeepingCustomException(e.getMessage());
            }
        }else {
            filterChain.doFilter(request,response);
        }

    }

    /**
     * shouldNot filter method neglects the given endpoint for validation
     * @param request
     * @return - returns boolean true/false weather the endpoint is valid or not
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        RequestMatcher requestMatcher = new NegatedRequestMatcher(matcher);
        return requestMatcher.matches(request);
    }
}
