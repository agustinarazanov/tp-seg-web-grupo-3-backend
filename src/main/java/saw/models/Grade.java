package saw.models;

import jakarta.persistence.*;

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

    public Grade() {
    }

    public Grade(int value, User user, Subject subject) {
        this.value = value;
        this.user = user;
        this.subject = subject;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Grade{" + "id=" + this.id + ", value='" + this.value + '\'' + ", user='" + this.user.getName() + '\'' + ", subject='" + this.subject.getName() + '\'' + '}';
    }
}
