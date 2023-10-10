package cdw.springTraining.gatekeeper.dao;


import cdw.springTraining.gatekeeper.entity.RegistrationApprovalList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author sakthivel
 * RegistrationApprovalListRepository class has the jpa methods of registration approval list
 */
public interface RegistrationApprovalListRepository extends JpaRepository<RegistrationApprovalList,Integer> {
}
