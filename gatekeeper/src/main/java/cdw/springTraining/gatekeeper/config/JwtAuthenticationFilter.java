package cdw.springTraining.gatekeeper.config;

import cdw.springTraining.gatekeeper.customException.GateKeepingCustomException;
import cdw.springTraining.gatekeeper.dao.TokenRepository;
import cdw.springTraining.gatekeeper.dao.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author sakthivel
 * JwtAuthenticationFilter is used to validate the Jwt token of user
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final TokenRepository tokenRepository;


    @Value("${secret.key}")
    private String secretKey;

    /**
     * doFilterInternal method validates the jwt token
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
                userRepository.findByMail(username).get();
                    String[] roles=decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
                    Arrays.stream(roles).forEach(role->{
                        authorities.add(new SimpleGrantedAuthority(role));
                    });

                    if(tokenRepository.existsByExpiredAndRevoked(false,false))
                    {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,null,authorities);
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        filterChain.doFilter(request,response);
                    }
                    else{
                        throw new GateKeepingCustomException("token not valid");
                    }
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
        String path = request.getRequestURI();
        return "/gate-keeping/visitor/residentCheck".equals(path);
    }
}
