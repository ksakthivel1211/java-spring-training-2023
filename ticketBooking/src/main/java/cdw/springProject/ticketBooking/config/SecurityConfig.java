package cdw.springProject.ticketBooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource)
    {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery("select user_id,password from user where user_id=?");

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select user_id,role_name from role where user_id=?");
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(configure->
                configure
                        .requestMatchers(HttpMethod.GET,"/ticket-booking/admin").hasRole("admin")
                        .requestMatchers(HttpMethod.GET,"/ticket-booking/business-user").hasRole("businessUser")
                        .requestMatchers(HttpMethod.GET,"/ticket-booking").hasAnyRole("admin","businessUse","endUser")

        );
        http.httpBasic(Customizer.withDefaults());

        http.csrf().disable();
        return http.build();

    }
}
