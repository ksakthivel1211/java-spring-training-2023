package cdw.springProject.ticketBooking.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String mail;
    private String userName;
    private String accessToken;
    private String refresh_token;
}
