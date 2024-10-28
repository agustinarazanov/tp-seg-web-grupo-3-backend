package saw.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Grade {
    @Id
    @GeneratedValue
    private Long id;
    private int value;
    @ManyToOne
    private User user;
    @ManyToOne
    private Subject subject;

    public Grade() {}

    public Grade(int value, User user, Subject subject) {
        this.value = value;
        this.user = user;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Grade{" + "id=" + this.id + ", value='" + this.value + '\'' + ", user='" + this.user.getName() + '\'' + ", subject='" + this.subject.getName() + '\'' + '}';
    }
}
