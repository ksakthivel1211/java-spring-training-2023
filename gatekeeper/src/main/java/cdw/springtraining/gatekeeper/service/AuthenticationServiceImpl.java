package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.custom.exception.GateKeepingCustomException;
import cdw.springtraining.gatekeeper.dao.TokenRepository;
import cdw.springtraining.gatekeeper.dao.UserRepository;
import cdw.springtraining.gatekeeper.entity.Token;
import cdw.springtraining.gatekeeper.entity.TokenType;
import cdw.springtraining.gatekeeper.entity.User;
import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.AuthenticationResponseVo;
import cdw.springtraining.gatekeeper.model.AuthenticationRequest;
import cdw.springtraining.gatekeeper.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;


import java.util.*;

import static cdw.springtraining.gatekeeper.constant.ErrorConstants.USER_NOT_FOUND_BY_MAIL;

/**
 * @author sakthivel
 * Authentication service implementation has the authentication methods of login operations
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

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
    public AuthenticationResponseVo authenticate(AuthenticationRequest authenticationRequest)
    {
        try{
            User user = userRepository.findByMail(authenticationRequest.getMail()).orElseThrow(()-> new GateKeepingCustomException(USER_NOT_FOUND_BY_MAIL, HttpStatus.NOT_FOUND));

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getMail(),authenticationRequest.getPassword()));
            Set<String> roles = new HashSet<>();
            if (user != null) {
                roles.add(user.getRoleName());
            }
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            roles.stream().forEach(roleName ->{
                authorities.add(new SimpleGrantedAuthority(roleName));
            });
            String jwtAccessToken = jwtServiceImpl.genToken(user, authorities);

            revokeAllUserTokens(user);
            Token token = Token.builder()
                    .user(user)
                    .tokenName(jwtAccessToken)
                    .tokenType(TokenType.BEARER)
                    .revoked(false)
                    .expired(false)
                    .build();

            tokenRepository.save(token);

            AuthenticationResponseVo authenticationResponse = new AuthenticationResponseVo();

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
        List<Token> validUserToken = tokenRepository.findByExpiredAndRevokedAndUser(false,false,user);
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
