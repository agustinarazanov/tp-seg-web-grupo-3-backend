package saw.models;

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
    @ManyToMany
    private List<User> users;

    public Subject() {}

    public Subject(Long id, String name) {
        this.name = name;
        this.users = new ArrayList<>();
    }

    public boolean hasUsers() {
        return !this.users.isEmpty();
    }

    public void addUser(User user) {
        if (getUser(id).isEmpty())
            this.users.add(user);
    }

    public Optional<User> getUser(Long id) {
        return users.stream().filter(student -> Objects.equals(student.getId(), id)).findFirst();
    }

    @Override
    public String toString() {
        return "Subject{" + "id=" + this.id + ", name='" + this.name + '\'' + ", users='" + this.users.stream().map(User::getName).collect(Collectors.joining(", ")) + '\'' + '}';
    }
}
