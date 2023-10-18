package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.dao.UserRepository;
import cdw.springTraining.gatekeeper.dao.VisitorSlotRepository;
import cdw.springTraining.gatekeeper.entity.User;
import cdw.springTraining.gatekeeper.model.VisitorRequest;
import cdw.springTraining.gatekeeper.entity.VisitorSlot;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
import cdw.springTraining.gatekeeper.model.VisitorPassResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;
import static com.auth0.jwt.algorithms.Algorithm.HMAC256;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VisitorServiceImplTest {

    @InjectMocks
    VisitorServiceImpl visitorService;

    @Mock
    VisitorSlotRepository visitorSlotRepository;

    @Mock
    UserRepository userRepository;

//    @Value("${secret.key}")
//    private String secretKey;


    @Test
    public void testKeyGen()
    {
        String secretKey = "123";
        String mail = "sakthi@gmail.com";
        String name = "sakthi";
        VisitorRequest request = new VisitorRequest();
        request.setEmail(mail);
        VisitorSlot visitorSlot = new VisitorSlot();
        visitorSlot.setMail(mail);
        visitorSlot.setVisitorName(name);
        when(visitorSlotRepository.findByMail(mail)).thenReturn(Optional.of(visitorSlot));
        Algorithm algorithm= HMAC256(secretKey.getBytes());
        String pass =  JWT.create()
                .withSubject(visitorSlot.getMail())
                .withExpiresAt(new Date(System.currentTimeMillis()+5*60*1000))
                .sign(algorithm);
        VisitorPassResponse visitorPassResponse = new VisitorPassResponse();
        visitorPassResponse.setMail(mail);
        visitorPassResponse.setUserName(name);
        visitorPassResponse.setVisitorPass(pass);
        assertEquals(visitorPassResponse,visitorService.keyGen(request));
    }

    @Test
    public void testCheckResident()
    {
        String mail = "sakthi@gmail.com";
        String residentChecked = "in";
        VisitorRequest request = new VisitorRequest();
        request.setEmail(mail);
        User user = new User();
        user.setChecked(residentChecked);
        user.setMail(mail);
        when(userRepository.findByMail(mail)).thenReturn(Optional.of(user));
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("The resident has checked-" + residentChecked);
        assertEquals(controllerResponse,visitorService.checkResident(request));
    }
}
