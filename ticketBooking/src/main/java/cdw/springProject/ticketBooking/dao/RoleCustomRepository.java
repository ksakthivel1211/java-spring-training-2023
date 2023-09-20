package cdw.springProject.ticketBooking.dao;

import cdw.springProject.ticketBooking.entity.Role;
import cdw.springProject.ticketBooking.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public List<Role> getRole(User user){
        StringBuilder sql =new StringBuilder()
                .append("SELECT r.role_name as name \n" +
                        "FROM user u\n" +
                        "JOIN user_role ur ON u.user_id=ur.user_id\n" +
                        "JOIN role r ON r.role_id=ur.role_id\n"+
                        "Where 1=1");
        if(user.getMail()!=null){
            sql.append(" and email=:email");
        }
        NativeQuery<Role> query=((Session)entityManager.getDelegate()).createNativeQuery(sql.toString());
        if(user.getMail()!= null){
            query.setParameter("email",user.getMail());
        }
        query.addScalar("name", StandardBasicTypes.STRING);
        query.setResultListTransformer(Transformers.aliasToBean(Role.class));
        return query.list();
    }
}
