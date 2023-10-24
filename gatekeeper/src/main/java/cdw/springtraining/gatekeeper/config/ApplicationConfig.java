package cdw.springtraining.gatekeeper.config;

import cdw.springtraining.gatekeeper.custom.exception.GateKeepingCustomException;
import cdw.springtraining.gatekeeper.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author sakthivel
 * ApplicationConfig file contains Password encoder, User detail service and Authentication provider for the security aspects
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    @Autowired
    private final UserRepository userRepository;

    /**
     * passwordEncoder method provides the encoder type
     * @return - returns Encoder type
     */
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /**
     * authentication manager method provides authentication manager from authentication configuration
     * @param authenticationConfiguration
     * @return - returns authenticator manager
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * User detail manager maps the user to the user detail service
     * @return - returns user name of user detail
     */
    @Bean
    UserDetailsService userDetailsService()
    {
        return username -> userRepository.findByMail(username).orElseThrow(()-> new GateKeepingCustomException("User with the given mail is not found"));
    }

    /**
     * Authentication provider methods maps authentication provider to current instance
     * @return - returns Authentication provider
     */
    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}
