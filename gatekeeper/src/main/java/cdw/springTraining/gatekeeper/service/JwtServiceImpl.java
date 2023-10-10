package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.constant.TimeConstant;
import cdw.springTraining.gatekeeper.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static cdw.springTraining.gatekeeper.constant.TimeConstant.USER_TOKEN_TIME;
import static com.auth0.jwt.algorithms.Algorithm.HMAC256;

/**
 * @author sakthivel
 * Jwt service implementation has the functional method for generating token
 */
@Service
public class JwtServiceImpl {

    @Value("${secret.key}")
    private String secretKey;

    /**
     * genToken method generates token
     * @param user
     * @param authorities
     * @return returns Jwt token of type String
     */
    public String genToken(User user,Collection<SimpleGrantedAuthority> authorities){
        Algorithm algorithm= HMAC256(secretKey.getBytes());
        return JWT.create()
                .withSubject(user.getMail())
                .withExpiresAt(new Date(System.currentTimeMillis()+USER_TOKEN_TIME))
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

}
