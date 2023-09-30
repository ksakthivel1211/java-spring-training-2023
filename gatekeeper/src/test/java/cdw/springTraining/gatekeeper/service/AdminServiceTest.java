package cdw.springTraining.gatekeeper.service;

import cdw.springTraining.gatekeeper.dao.BlackListRepository;
import cdw.springTraining.gatekeeper.dao.RegistrationApprovalListRepository;
import cdw.springTraining.gatekeeper.dao.UserRepository;
import cdw.springTraining.gatekeeper.entity.RegistrationApprovalList;
import cdw.springTraining.gatekeeper.entity.User;
import cdw.springTraining.gatekeeper.service.AdminService;
import org.apache.el.stream.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private RegistrationApprovalListRepository registrationApprovalListRepository;

    @Mock
    private BlackListRepository blackListRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminService adminService;

//    @Test
//    public void testListAll()
//    {
//        List<RegistrationApprovalList>  approvalLists= new ArrayList<>();
//
//        when(registrationApprovalListRepository.findAll()).thenReturn(approvalLists);
//
//    }


}
