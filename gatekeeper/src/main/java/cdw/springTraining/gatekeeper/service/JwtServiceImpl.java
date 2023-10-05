package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC256;

@Service
public class JwtServiceImpl {

    @Value("${secret.key}")
    private String secretKey;

    /**
     *
     * @param user
     * @param authorities
     * @return
     */
    public String genToken(User user,Collection<SimpleGrantedAuthority> authorities){
        Algorithm algorithm= HMAC256(secretKey.getBytes());
        return JWT.create()
                .withSubject(user.getMail())
                .withExpiresAt(new Date(System.currentTimeMillis()+50*60*1000))
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

}
