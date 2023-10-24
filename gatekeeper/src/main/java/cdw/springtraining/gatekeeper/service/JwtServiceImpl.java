package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.constant.TimeConstant;
import cdw.springtraining.gatekeeper.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

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
    public String genToken(User user, Collection<SimpleGrantedAuthority> authorities){
        Algorithm algorithm= HMAC256(secretKey.getBytes());
        return JWT.create()
                .withSubject(user.getMail())
                .withExpiresAt(new Date(System.currentTimeMillis()+ TimeConstant.USER_TOKEN_TIME))
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    /**
     * Extract the remaining time in minutes until the JWT token expires
     * @param token - users token
     * @return - remaining time left in the time
     */
    public long extractRemainingTime(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        Date expirationTime = decodedJWT.getExpiresAt();
        Date currentTime = new Date();
        long remainingMillis = expirationTime.getTime() - currentTime.getTime();
        return remainingMillis ;
    }

}
