package cdw.springProject.ticketBooking.request;

import cdw.springProject.ticketBooking.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class RegistrationRequest {

    private String name;
    private int age;
    private String mail;
    private String password;
    private String role;

    public User returnUser(RegistrationRequest registrationRequest)
    {
        return new User(registrationRequest.name,registrationRequest.age,registrationRequest.mail,registrationRequest.password);
    }
}
