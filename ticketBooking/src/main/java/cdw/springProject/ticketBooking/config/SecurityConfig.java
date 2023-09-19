package cdw.springProject.ticketBooking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource)
//    {
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        jdbcUserDetailsManager.setUsersByUsernameQuery("select user_id,password from user where user_id=?");
//
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select user_id,role_name from role where user_id=?");
//        return jdbcUserDetailsManager;
//    }

//    @Bean
//    public UserDetailsService userDetailsService()
//    {
//        return new UserInfoDetailsService();
//    }

//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    public SecurityConfig(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests(configure->
                configure
                        .requestMatchers("/").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/ticket-booking/admin").hasRole("admin")
//                        .requestMatchers(HttpMethod.GET,"/ticket-booking/business-user").hasRole("businessUser")
//                        .requestMatchers(HttpMethod.GET,"/ticket-booking").hasAnyRole("admin","businessUse","endUser")
                        .requestMatchers(HttpMethod.POST,"/ticket-booking/admin/location").permitAll()
                        .requestMatchers(HttpMethod.POST,"/ticket-booking/admin/**").permitAll()
        )
                .csrf(csrf -> csrf.disable());


//        http.httpBasic(Customizer.withDefaults());

        return http.build();

    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider()
//    {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        return  daoAuthenticationProvider;
//    }
}
