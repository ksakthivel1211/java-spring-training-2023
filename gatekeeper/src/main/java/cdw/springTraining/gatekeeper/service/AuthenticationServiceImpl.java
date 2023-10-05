package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.customException.GateKeepingCustomException;
import cdw.springTraining.gatekeeper.dao.TokenRepository;
import cdw.springTraining.gatekeeper.dao.UserRepository;
import cdw.springTraining.gatekeeper.entity.Token;
import cdw.springTraining.gatekeeper.entity.TokenType;
import cdw.springTraining.gatekeeper.entity.User;
import cdw.springTraining.gatekeeper.model.AuthenticationRequest;
import cdw.springTraining.gatekeeper.model.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtServiceImpl jwtServiceImpl;
    private TokenRepository tokenRepository;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtServiceImpl jwtServiceImpl, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtServiceImpl = jwtServiceImpl;
        this.tokenRepository=tokenRepository;
    }

    /**
     * Authenticate method validates the user details and provides access token
     * @param authenticationRequest
     * @return - AuthenticationResponse of token and user details
     */
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest)
    {
        try{
            User user = userRepository.findByMail(authenticationRequest.getMail()).orElseThrow(()-> new GateKeepingCustomException("user not found"));

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getMail(),authenticationRequest.getPassword()));
            List<String> roles = new ArrayList<>();
            if (user != null) {
                roles.add(user.getRoleName());
            }
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Set<String> set = new HashSet<>();
            roles.stream().forEach(roleName ->{
                set.add(roleName);
                authorities.add(new SimpleGrantedAuthority(roleName));
            });
            set.stream().forEach(name -> authorities.add(new SimpleGrantedAuthority(name)));
            var jwtAccessToken = jwtServiceImpl.genToken(user, authorities);

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
            return authenticationResponse;
        }
        catch(NoSuchElementException e){
            throw new GateKeepingCustomException(e.getMessage());
        }
        catch (BadCredentialsException e){
            throw new GateKeepingCustomException(e.getMessage());
        }
        catch (Exception e){
            throw new GateKeepingCustomException(e.getMessage());
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
