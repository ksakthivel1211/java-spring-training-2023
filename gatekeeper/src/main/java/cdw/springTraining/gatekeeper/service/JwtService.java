package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC256;

@Service
public class JwtService {

    @Value("${secret.key}")
    private String secretKey;

    public String genToken(User user,SimpleGrantedAuthority authorities){
        Algorithm algorithm= HMAC256(secretKey.getBytes());
        return JWT.create()
                .withSubject(user.getMail())
                .withExpiresAt(new Date(System.currentTimeMillis()+50*60*1000))
                .withClaim("roles",authorities.getAuthority())
                .sign(algorithm);
    }

    public String generateRefreshToken(User user, SimpleGrantedAuthority authorities){
        Algorithm algorithm= HMAC256(secretKey.getBytes());
        return JWT.create()
                .withSubject(user.getMail())
                .withExpiresAt(new Date(System.currentTimeMillis()+70*60*1000))
                .sign(algorithm);
    }

//    private final String SECRET="5367566B59703373367639792F423F4528482B4D621655468576D5A71347437";
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }

//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
}
