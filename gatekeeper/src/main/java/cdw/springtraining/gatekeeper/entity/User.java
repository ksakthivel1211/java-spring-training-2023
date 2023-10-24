package cdw.springtraining.gatekeeper.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author sakthivel
 * User entity class has the details of users
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name="user_name")
    @NotNull(message = "user name is required")
    private String name;

    @Column(name="age")
    private int age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "mail")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",message = "wrong email format")
    @NotNull(message = "email is required")
    private String mail;

    @Column(name = "password")
    @NotNull(message = "password is required")
    private String password;

    @Column(name = "role_name")
    @NotNull(message = "role name is required")
    private String roleName;

    @Column(name = "checked")
    private String checked;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST} , mappedBy = "user")
    private List<Token> tokens;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST} , mappedBy = "user")
    private List<VisitorSlot> visitorSlots;

    public void addToVisitorSlot(VisitorSlot slot)
    {
        if(visitorSlots==null)
        {
            visitorSlots = new ArrayList<>();
        }
        visitorSlots.add(slot);
    }

    public void removeFromVisitorSlot(VisitorSlot slot)
    {
        visitorSlots.remove(slot);
    }

    public User(String name, int age, String gender, String mail, String password, String roleName) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.mail = mail;
        this.password = password;
        this.roleName=roleName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(roleName));

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
