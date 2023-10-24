package cdw.springtraining.gatekeeper.service;

import cdw.springtraining.gatekeeper.model.ControllerResponse;
import cdw.springtraining.gatekeeper.model.BlackListRequest;
import cdw.springtraining.gatekeeper.dao.BlackListRepository;
import cdw.springtraining.gatekeeper.service.BlackListServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BlackListServiceImplTest {

    @InjectMocks
    private BlackListServiceImpl blackListServiceImpl;

    @Mock
    private BlackListRepository blackListRepository;

//    @Test
//    public void testAddToBlackList()
//    {
//        String mail = "ram@gmail.com";
//        BlackListRequest request = new BlackListRequest();
//        request.setMail(mail);
//        request.setName("ram");
//        request.setUserId(1);
//        ControllerResponse controllerResponse = new ControllerResponse();
//        controllerResponse.setMessage("The user has been black listed");
//        assertEquals(controllerResponse, blackListServiceImpl.addToBlackList(request));
//    }
}
