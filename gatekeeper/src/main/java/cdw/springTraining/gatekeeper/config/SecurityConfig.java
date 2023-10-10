package cdw.springTraining.gatekeeper.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author sakthivel
 * SecurityConfig class has security configurations
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtVisitorAuthFilter visitorAuthFilter;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configure ->
                        configure
                                .requestMatchers("/registration/**").permitAll()
                                .requestMatchers("/admin/**").hasAuthority("admin")
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/logout").permitAll()
                                .requestMatchers("/resident/**").hasAuthority("resident")
                                .requestMatchers("/gate-keeper/**").hasAuthority("gateKeeper")
                                .requestMatchers("/visitor/**").permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(visitorAuthFilter, BasicAuthenticationFilter.class)
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        ;

        return http.build();
    }
}
