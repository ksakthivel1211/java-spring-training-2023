package cdw.springtraining.gatekeeper.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

/**
 * @author sakthivel
 * RegistrationApprovalList entity class has the regsitration request details made by the users
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registration_approval_list")
public class RegistrationApprovalList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approval_id")
    private int approval_id;

    @Column(name = "status")
    private String status;

    @Column(name="user_name")
    @NotNull(message = "name is required")
    private String name;

    @Column(name="age")
    @Range(min=18,max=100)
    private int age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String mail;

    @Column(name = "password")
    @NotNull(message = "password is required")
    private String password;

    @Column(name = "role_name")
    @NotNull(message = "role name is required")
    private String roleName;

    public RegistrationApprovalList(String status, String name, int age, String gender, String mail, String password, String roleName) {
        this.status = status;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.mail = mail;
        this.password = password;
        this.roleName = roleName;
    }
}
