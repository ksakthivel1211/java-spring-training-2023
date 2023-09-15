package cdw.springProject.ticketBooking.service;

import cdw.springProject.ticketBooking.dao.UserRepository;
import cdw.springProject.ticketBooking.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class LoginService {

    private UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository theUserRepository)
    {
        userRepository=theUserRepository;
    }

    public String loginUser(String email,String password)
    {
        try
        {
            User user = userRepository.findByMail(email);
            if(user!=null)
            {
                if(user.getPassword().equals(password))
                {
                    return "Login successful";
                }
                else
                {
                    throw new RuntimeException();
                }
            }
            else
            {
                throw new RuntimeException();
            }
        }
        catch (Exception exception)
        {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
