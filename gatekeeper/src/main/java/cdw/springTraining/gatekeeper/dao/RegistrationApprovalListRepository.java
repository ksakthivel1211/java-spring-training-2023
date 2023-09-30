package cdw.springTraining.gatekeeper.dao;


import cdw.springTraining.gatekeeper.entity.RegistrationApprovalList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationApprovalListRepository extends JpaRepository<RegistrationApprovalList,Integer> {
}
