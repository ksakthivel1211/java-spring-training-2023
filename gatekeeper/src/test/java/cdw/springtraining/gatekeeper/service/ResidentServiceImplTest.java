package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.VisitorSlotRequest;
import cdw.springtraining.gatekeeper.dao.UserRepository;
import cdw.springtraining.gatekeeper.dao.VisitorSlotRepository;
import cdw.springtraining.gatekeeper.entity.User;
import cdw.springtraining.gatekeeper.entity.VisitorSlot;
import cdw.springtraining.gatekeeper.utils.UserInformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResidentServiceImplTest {

    @InjectMocks
    private ResidentServiceImpl residentServiceImpl;

    @Mock
    private VisitorSlotRepository visitorSlotRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testBookVisitorSlot()
    {
        String mail = "sam@gmail.com";
        Mockito.mockStatic(UserInformation.class);
        VisitorSlotRequest visitorSlotRequest = new VisitorSlotRequest();
        LocalDate date = LocalDate.now();
        OffsetDateTime dateTime = OffsetDateTime.now();
        visitorSlotRequest.setDate(date);
        visitorSlotRequest.setVisitorName("sakthi");
        visitorSlotRequest.setInTime(dateTime);
        visitorSlotRequest.setOutTime(dateTime);
        User user = new User("sam",21,"male",mail,"abc","resident");
        user.setUserId(1);
        when(userRepository.findByMail(mail)).thenReturn(Optional.of(user));
        when(UserInformation.getUserName()).thenReturn(mail);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("Slot for the visitor has been booked");
        assertEquals(controllerResponse, residentServiceImpl.bookVisitorSlot(visitorSlotRequest));
    }

    @Test
    public void testRemoveVisitorSlot()
    {
        int slotId=1;
        String name = "sakthi";
        LocalDate date = LocalDate.parse("2024-10-07");
        OffsetDateTime inTime = OffsetDateTime.parse("2024-10-07T17:32:28Z");
        OffsetDateTime outTime = OffsetDateTime.parse("2024-10-07T17:32:28Z");
        String mail = "sakthi@gmail.com";
        User user = new User("sam",21,"male","sam@gmail.com","abc","resident");
        VisitorSlot visitorSlot = new VisitorSlot(name,mail,date,inTime,outTime);
        visitorSlot.setStatus("approved");
        user.addToVisitorSlot(visitorSlot);
        visitorSlot.setUser(user);
//        when(visitorSlotRepository.findById(slotId)).thenReturn(Optional.of(visitorSlot));
    }

//    @Test
//    public void testUserChecked()
//    {
//        String mail = "sakthi@gmail.com";
//
//        Collection<GrantedAuthority> roles = new ArrayList<>();
//
//        roles.add((GrantedAuthority) () -> "resident");
//
//        String checked = "in";
//        User user = new User();
//        Mockito.mockStatic(UserInformation.class);
//        when(UserInformation.getRoles()).thenReturn(roles);
//        when(UserInformation.getUserName()).thenReturn(mail);
//        when(userRepository.findByMail(mail)).thenReturn(Optional.of(user));
//        ControllerResponse controllerResponse = new ControllerResponse();
//        controllerResponse.setMessage("The resident has checked"+checked);
//        assertEquals(controllerResponse, residentServiceImpl.userChecked(checked));
//    }
}
