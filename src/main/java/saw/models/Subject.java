package saw.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Entity
public class Subject {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    private User teacher;
    @ManyToMany @JsonIgnore
    private List<User> students;

    public Subject() {
        this.students = new ArrayList<>();
    }

    public Subject(String name) {
        this.name = name;
        this.students = new ArrayList<>();
    }

    public boolean hasStudents() {
        return !this.students.isEmpty();
    }

    public Optional<User> getStudent(Long id) {
        return students.stream().filter(student -> Objects.equals(student.getId(), id)).findFirst();
    }

    public void addStudent(User user) {
        if (getStudent(id).isEmpty())
            this.students.add(user);
    }

    @Override
    public String toString() {
        return "Subject{" + "id=" + this.id + ", name='" + this.name + '\'' + ", users='" + this.students.stream().map(User::getName).collect(Collectors.joining(", ")) + '\'' + '}';
    }
}
