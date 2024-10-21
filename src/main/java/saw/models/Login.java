package saw.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Login {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToOne
    private User user;

    public Login() {}

    public Login(Long id, String email, User user, String password) {
        this.email = email;
        this.user = user;
        this.password = password;
    }
}
