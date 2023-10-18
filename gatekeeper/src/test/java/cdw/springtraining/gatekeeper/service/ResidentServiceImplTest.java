package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.VisitorSlotRequest;
import cdw.springtraining.gatekeeper.dao.UserRepository;
import cdw.springtraining.gatekeeper.dao.VisitorSlotRepository;
import cdw.springtraining.gatekeeper.entity.User;
import cdw.springtraining.gatekeeper.entity.VisitorSlot;
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
        VisitorSlotRequest visitorSlotRequest = new VisitorSlotRequest();
        LocalDate date = LocalDate.now();
        OffsetDateTime dateTime = OffsetDateTime.now();
        visitorSlotRequest.setUserId(1);
        visitorSlotRequest.setDate(date);
        visitorSlotRequest.setVisitorName("sakthi");
        visitorSlotRequest.setInTime(dateTime);
        visitorSlotRequest.setOutTime(dateTime);
        User user = new User("sam",21,"male","sam@gmail.com","abc","resident");
        user.setUserId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("Slot for the visitor has been booked");
        assertEquals(controllerResponse, residentServiceImpl.bookVisitorSlot(visitorSlotRequest));
    }

    @Test
    public void testRemoveVisitorSlot()
    {
        int slotId=1;
        String name = "sakthi";
        LocalDate date = LocalDate.parse("2021-01-07");
        OffsetDateTime inTime = OffsetDateTime.parse("2023-10-13T17:32:28Z");
        OffsetDateTime outTime = OffsetDateTime.parse("2023-10-13T17:32:28Z");
        String mail = "sakthi@gmail.com";
        User user = new User("sam",21,"male","sam@gmail.com","abc","resident");
        VisitorSlot visitorSlot = new VisitorSlot(name,mail,date,inTime,outTime);
        user.addToVisitorSlot(visitorSlot);
        visitorSlot.setUser(user);
        when(visitorSlotRepository.findById(slotId)).thenReturn(Optional.of(visitorSlot));

        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("The visitor slot has been cancelled");

        assertEquals(controllerResponse, residentServiceImpl.removeVisitorSlot(slotId));
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
