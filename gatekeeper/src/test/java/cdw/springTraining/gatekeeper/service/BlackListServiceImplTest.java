package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.dao.BlackListRepository;
import cdw.springTraining.gatekeeper.model.BlackListRequest;
import cdw.springTraining.gatekeeper.model.ControllerResponse;
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

    @Test
    public void testAddToBlackList()
    {
        String mail = "ram@gmail.com";
        BlackListRequest request = new BlackListRequest();
        request.setMail(mail);
        request.setName("ram");
        request.setUserId(1);
        ControllerResponse controllerResponse = new ControllerResponse();
        controllerResponse.setMessage("The user has been black listed");
        assertEquals(controllerResponse, blackListServiceImpl.addToBlackList(request));
    }
}
