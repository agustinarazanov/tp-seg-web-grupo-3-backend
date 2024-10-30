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
    private User student;
    @ManyToOne
    private Subject subject;

    public Grade() {}

    public Grade(int value, User student, Subject subject) {
        this.value = value;
        this.student = student;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Grade{" + "id=" + this.id + ", value='" + this.value + '\'' + ", user='" + this.student.getName() + '\'' + ", subject='" + this.subject.getName() + '\'' + '}';
    }
}
