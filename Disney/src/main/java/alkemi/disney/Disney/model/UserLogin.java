package alkemi.disney.Disney.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserLogin {
    private String username;
    private String password;
}
