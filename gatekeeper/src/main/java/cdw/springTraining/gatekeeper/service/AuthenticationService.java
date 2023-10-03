package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.customException.GateKeepingCustomException;
import cdw.springTraining.gatekeeper.dao.TokenRepository;
import cdw.springTraining.gatekeeper.dao.UserRepository;
import cdw.springTraining.gatekeeper.entity.Token;
import cdw.springTraining.gatekeeper.entity.TokenType;
import cdw.springTraining.gatekeeper.entity.User;
import cdw.springTraining.gatekeeper.model.AuthenticationRequest;
import cdw.springTraining.gatekeeper.model.AuthenticationResponse;
import cdw.springTraining.gatekeeper.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private TokenRepository tokenRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService,TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tokenRepository=tokenRepository;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest)
    {
        try{
            User user = userRepository.findByMail(authenticationRequest.getMail()).orElseThrow(()-> new GateKeepingCustomException("user not found"));

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getMail(),authenticationRequest.getPassword()));
            List<String> roles = new ArrayList<>();
            if (user != null) {
//                user.getRoleName().stream().forEach(role -> roles.add(role));
                roles.add(user.getRoleName());
            }
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Set<String> set = new HashSet<>();
            roles.stream().forEach(roleName ->{
                set.add(roleName);
                authorities.add(new SimpleGrantedAuthority(roleName));
            });
//            user.setRoles(set);
            set.stream().forEach(name -> authorities.add(new SimpleGrantedAuthority(name)));
            var jwtAccessToken = jwtService.genToken(user, authorities);
            var jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);

//            if(authentication.isAuthenticated())
//            {
//                System.out.println("s");
//            }
//            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getName());
//
//            var jwtAccessToken = jwtService.genToken(user, a);
//            var jwtRefreshToken = jwtService.generateRefreshToken(user, authority);

            revokeAllUserTokens(user);
            var token = Token.builder()
                    .user(user)
                    .tokenName(jwtAccessToken)
                    .tokenType(TokenType.BEARER)
                    .revoked(false)
                    .expired(false)
                    .build();

            tokenRepository.save(token);

            AuthenticationResponse authenticationResponse = new AuthenticationResponse();

            authenticationResponse.setMail(user.getMail());
            authenticationResponse.setUserName(user.getName());
            authenticationResponse.setAccessToken(jwtAccessToken);
            authenticationResponse.setRefreshToken(jwtRefreshToken);
            return authenticationResponse;
        }
//        catch(NoSuchElementException e){
//            return new ResponseEntity<AuthenticationResponse>(HttpStatus.NOT_FOUND);
//        }
//        catch (BadCredentialsException e){
//            return new ResponseEntity<AuthenticationResponse>(HttpStatus.UNAUTHORIZED);
//        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void revokeAllUserTokens(User user)
    {
        var validUserToken = tokenRepository.findByExpiredAndRevokedAndUser(false,false,user);
        if(validUserToken.isEmpty()){
            return;
        }
        validUserToken.stream().forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserToken);
    }


}
