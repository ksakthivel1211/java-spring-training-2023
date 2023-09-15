package cdw.springProject.ticketBooking.dao;

import cdw.springProject.ticketBooking.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    public Role findByRoleName(String roleName);
}
