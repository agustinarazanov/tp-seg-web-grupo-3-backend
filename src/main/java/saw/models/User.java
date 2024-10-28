package saw.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String role;

    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;

    public User() {}

    public User(String name, String role, String email, String password) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
    }
}
