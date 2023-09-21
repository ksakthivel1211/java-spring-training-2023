package cdw.springProject.ticketBooking.service;

import cdw.springProject.ticketBooking.customException.BookingException;
import cdw.springProject.ticketBooking.dao.RoleRepository;
import cdw.springProject.ticketBooking.dao.UserRepository;
import cdw.springProject.ticketBooking.entity.Role;
import cdw.springProject.ticketBooking.entity.User;
import cdw.springProject.ticketBooking.request.RegistrationRequest;
import cdw.springProject.ticketBooking.responseModel.ControllerResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class RegistrationService {

    private UserRepository userRepository;
    private RegistrationRequest registrationRequest;
    private RoleRepository roleRepository;

    @Autowired
    public RegistrationService(UserRepository theUserRepository,RegistrationRequest theRegistrationRequest,RoleRepository theRoleRepository)
    {
        userRepository=theUserRepository;
        registrationRequest=theRegistrationRequest;
        roleRepository=theRoleRepository;
    }

    public ControllerResponse addUser(RegistrationRequest theRegistrationRequest)
    {
        try {
//            if(theRegistrationRequest.getRole().equals("endUser")) {
                if (theRegistrationRequest.getAge() > 18) {
                    User theUser = registrationRequest.returnUser(theRegistrationRequest);
                    theUser.setUserId(0);
                    Role userRole = roleRepository.findByRoleName(theRegistrationRequest.getRole());
                    theUser.addRoles(userRole);
                    User dbUser = userRepository.save(theUser);
                    return new ControllerResponse("User is been successfully added");
//                }
//                else {
//                    throw new BookingException("Below age limit");
//                }
            }
            else {
                throw new BookingException("only end user are allowed to register");
            }
        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
    }
}
