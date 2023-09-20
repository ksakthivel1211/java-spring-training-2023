package cdw.springProject.ticketBooking.config;

import cdw.springProject.ticketBooking.customException.BookingException;
import cdw.springProject.ticketBooking.dao.RoleCustomRepository;
import cdw.springProject.ticketBooking.dao.RoleRepository;
import cdw.springProject.ticketBooking.dao.UserRepository;
import cdw.springProject.ticketBooking.entity.Role;
import cdw.springProject.ticketBooking.entity.User;
import cdw.springProject.ticketBooking.service.BusinessUserService;
import cdw.springProject.ticketBooking.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private RoleCustomRepository roleCustomRepository;
    private BusinessUserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService, RoleCustomRepository roleCustomRepository, BusinessUserService userService,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.roleCustomRepository = roleCustomRepository;
        this.userService = userService;
        this.roleRepository=roleRepository;
    }

    public ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest)
    {
        try{
            User user = userRepository.findByMail(authenticationRequest.getMail());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getMail(),authenticationRequest.getPassword()));
            List<Role> roles = new ArrayList<>();
            if (user != null) {
                user.getRoles().stream().forEach(role -> roles.add(role));
            }
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Set<Role> set = new HashSet<>();
            roles.stream().forEach(c ->{set.add(new Role(c.getRoleName()));
                authorities.add(new SimpleGrantedAuthority(c.getRoleName()));
            });
            user.setRoles(set);
            set.stream().forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getRoleName())));
            var jwtAccessToken = jwtService.genToken(user, authorities);
            var jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);
            return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwtAccessToken).refresh_token(jwtRefreshToken).mail(user.getMail()).userName(user.getName()).build());
        }
        catch(NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}



















