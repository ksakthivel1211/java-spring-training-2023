package cdw.springtraining.gatekeeper.dao;


import cdw.springtraining.gatekeeper.entity.RegistrationApprovalList;
import cdw.springtraining.gatekeeper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author sakthivel
 * RegistrationApprovalListRepository class has the jpa methods of registration approval list
 */
public interface RegistrationApprovalListRepository extends JpaRepository<RegistrationApprovalList,Integer> {
    Optional<RegistrationApprovalList> findByMail(String mail);
    List<RegistrationApprovalList> findByStatus(String status);
}
