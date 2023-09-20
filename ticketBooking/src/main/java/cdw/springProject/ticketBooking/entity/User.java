package cdw.springProject.ticketBooking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @JsonManagedReference(value = "user-ticket")
    @OneToMany(fetch = FetchType.LAZY , mappedBy = "user")
    private List<Tickets> tickets;

    public User(String name, int age, String gender, String mail, String password) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.mail = mail;
        this.password = password;
    }

    public List<String> getRolesName()
    {
        List<String> rolesName = roles.stream().map(role -> role.getRoleName()).collect(Collectors.toList());
        return rolesName;
    }

    public void addRoles(Role userRole)
    {
        if(roles==null)
        {
            roles=new HashSet<>();
        }
        roles.add(userRole);
    }

    public void addTickets(Tickets theTickets)
    {
        if(tickets==null)
        {
            tickets = new ArrayList<>();
        }
        tickets.add(theTickets);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        roles.stream().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));

        return authorities;
    }

//    @Override
//    public String getPassword()
//    {
//        return password;
//    }

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
