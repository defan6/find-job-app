package my.ddos.user_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtResponse {

    private String accessToken;

    private String tokenType = "Bearer";

    private LocalDateTime expiresAt;

    private String username;

    public JwtResponse(String accessToken, String username, Long expirationMs){
        this.accessToken = accessToken;
        this.username = username;
        this.expiresAt = LocalDateTime.now()
                .plus(expirationMs, ChronoUnit.MILLIS);
    }
}
