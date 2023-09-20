package cdw.springProject.ticketBooking.service;

import cdw.springProject.ticketBooking.entity.RoleEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import cdw.springProject.ticketBooking.dao.RoleCustomRepository;
import cdw.springProject.ticketBooking.dao.UserRepository;
import cdw.springProject.ticketBooking.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class JwtService {

    private RoleCustomRepository roleCustomRepository;
    private UserRepository userRepository;

    @Autowired
    public JwtService(RoleCustomRepository roleCustomRepository, UserRepository userRepository) {
        this.roleCustomRepository = roleCustomRepository;
        this.userRepository = userRepository;
    }

    @Value("${secret.key}")
    private String secretKey;

    public String genToken(User user, Collection<SimpleGrantedAuthority> authorities){
        Algorithm algorithm=Algorithm.HMAC256(secretKey.getBytes());
        return JWT.create()
                .withSubject(user.getMail())
                .withExpiresAt(new Date(System.currentTimeMillis()+50*60*1000))
                .withClaim("roles",authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String generateRefreshToken(User user, Collection<SimpleGrantedAuthority> authorities){
        Algorithm algorithm=Algorithm.HMAC256(secretKey.getBytes());
        return JWT.create()
                .withSubject(user.getMail())
                .withExpiresAt(new Date(System.currentTimeMillis()+70*60*1000))
                .sign(algorithm);
    }

    private final String SECRET="5367566B59703373367639792F423F4528482B4D621655468576D5A71347437";
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails

    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .claim("roles", RoleEntity.endUser)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }




    private Claims extractAllClaims(String str){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(str)
                .getBody();

    }

    private Key getSignInKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
