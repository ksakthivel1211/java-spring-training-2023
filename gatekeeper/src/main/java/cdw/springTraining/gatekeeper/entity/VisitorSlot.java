package cdw.springTraining.gatekeeper.entity;

import cdw.springTraining.gatekeeper.model.UserResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * @author sakthivel
 * VisitorSlot entity class has the details of visitor slot booked by the user
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "visitor_slot")
public class VisitorSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slot_id")
    private int slotId;

    @Column(name = "visitor_name")
    private String visitorName;

    @Column(name = "mail",unique = true)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String mail;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "in_time")
    private OffsetDateTime inTime;

    @Column(name = "out_time")
    private OffsetDateTime outTime;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;

    public VisitorSlot(String visitorName, String mail, LocalDate date, OffsetDateTime inTime, OffsetDateTime outTime) {
        this.visitorName = visitorName;
        this.mail = mail;
        this.date = date;
        this.inTime = inTime;
        this.outTime = outTime;
    }

    public OffsetDateTime getOffSetInTime()
    {
        return OffsetDateTime.from(inTime);
    }
    public OffsetDateTime getOffSetOutTime()
    {
        return OffsetDateTime.from(outTime);
    }

    public UserResponse getUserResponse()
    {
        UserResponse response = new UserResponse();
        response.setName(user.getName());
        response.setAge(user.getAge());
        response.setGender(user.getGender());
        response.setMail(user.getMail());
        return response;
    }
}
