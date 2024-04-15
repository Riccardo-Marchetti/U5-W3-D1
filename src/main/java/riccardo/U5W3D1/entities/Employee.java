package riccardo.U5W3D1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue
    @Setter (AccessLevel.NONE)
    private UUID id;

    private String username;

    private String name;

    private String surname;

    private String email;

    private String avatar;

    @OneToMany (mappedBy = "employee")
    @JsonIgnore
    private List<Device> device;

    public Employee(String username, String name, String surname, String email) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
//        this.avatar = "https://ui-avatars.com/api/?name=" + name + "+" + surname;
    }
}
