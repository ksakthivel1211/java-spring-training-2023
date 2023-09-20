package cdw.springProject.ticketBooking.request;

import cdw.springProject.ticketBooking.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class RegistrationRequest {

    private String name;
    private int age;
    private String gender;
    private String mail;
    private String password;
    private String role;



    public User returnUser(RegistrationRequest registrationRequest)
    {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());
        return new User(registrationRequest.name,registrationRequest.age,registrationRequest.gender,registrationRequest.mail,encodedPassword);
    }
}
