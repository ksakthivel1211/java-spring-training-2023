package cdw.springtraining.gatekeeper;

import cdw.springtraining.gatekeeper.dao.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(SecurityFilterAutoConfiguration.class)

@SpringBootApplication
public class GatekeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatekeeperApplication.class, args);
	}

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository) {

        return runner -> {

//            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            String encodedPassword = passwordEncoder.encode("abc123");
//            User admin = new User("sakthi",21,"male","sakthi@gmail.com",encodedPassword,"admin");
//            userRepository.save(admin);

        };

    }

}
