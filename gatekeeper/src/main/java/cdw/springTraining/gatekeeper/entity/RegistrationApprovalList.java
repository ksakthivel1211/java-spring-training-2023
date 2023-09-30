package cdw.springTraining.gatekeeper.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String name;

    @Column(name="age")
    private int age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email")
    private String mail;

    @Column(name = "password")
    private String password;

    @Column(name = "role_name")
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
