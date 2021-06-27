package alkemi.disney.Disney.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    private String dni;

    @NotNull
    private String mail;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Provide status(ROLE_USER/ROLE_ADMIN)")
    @Column(name = "user_type")
    private User.Type userType;

    public enum Type{
        ROLE_USER,ROLE_ADMIN;
    }

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "update_at")
    private String updateAt;

}
