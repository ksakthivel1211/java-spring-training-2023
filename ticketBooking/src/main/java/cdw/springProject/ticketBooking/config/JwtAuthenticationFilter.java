package cdw.springProject.ticketBooking.config;

import cdw.springProject.ticketBooking.customException.BookingException;
import cdw.springProject.ticketBooking.dao.UserRepository;
import cdw.springProject.ticketBooking.service.JwtService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserDetailsService userDetailsService;

    @Autowired
    JwtService jwtService;


    @Value("${secret.key}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            try{
                String token = authHeader.substring("Bearer ".length());
                Algorithm algorithm=Algorithm.HMAC256((secretKey.getBytes()));
                JWTVerifier verifier=JWT.require(algorithm).build();
                DecodedJWT decodedJWT=verifier.verify(token);
                String username=decodedJWT.getSubject();
                if(userRepository.findByMail(username)!=null)
                {
                    String[] roles=decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
                    Arrays.stream(roles).forEach(role->{
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,null,authorities);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    filterChain.doFilter(request,response);
                }
                else{
                    throw new BookingException("invalid token");
                }

            } catch (Exception e) {
                throw new BookingException(e.getMessage());
            }
        }else {
            filterChain.doFilter(request,response);
        }
    }
}
