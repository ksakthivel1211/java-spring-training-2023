package cdw.springtraining.gatekeeper.dao;


import cdw.springtraining.gatekeeper.entity.RegistrationApprovalList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author sakthivel
 * RegistrationApprovalListRepository class has the jpa methods of registration approval list
 */
public interface RegistrationApprovalListRepository extends JpaRepository<RegistrationApprovalList,Integer> {
}
