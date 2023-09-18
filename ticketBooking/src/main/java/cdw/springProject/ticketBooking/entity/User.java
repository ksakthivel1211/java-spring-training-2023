package cdw.springProject.ticketBooking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
public class User {

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

    @JsonManagedReference(value = "user-role")
    @OneToMany(fetch = FetchType.LAZY , mappedBy = "user")
    private List<Role> roles;

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

    public void addRoles(Role userRole)
    {
        if(roles==null)
        {
            roles=new ArrayList<>();
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
}
