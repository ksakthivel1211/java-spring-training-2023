package cdw.springtraining.gatekeeper.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sakthivel
 * BlackList entity class has the details of Black listed users
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "black_list")
public class BlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "black_list_id")
    private int blackListId;

    @Column(name = "name")
    private String name;

    @Column(name = "mail")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String mail;

    @Column(name = "user_id")
    private int userId;

    public BlackList(String name, String mail, int userId) {
        this.name = name;
        this.mail = mail;
        this.userId = userId;
    }
}
