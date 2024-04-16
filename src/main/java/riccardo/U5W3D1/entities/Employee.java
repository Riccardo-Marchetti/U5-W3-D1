package riccardo.U5W3D1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import riccardo.U5W3D1.enums.Role;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties ({"password", "role", "authorities", "credentialsNonExpired", "accountNonExpired", "accountNonLocked", "enabled"})
public class Employee implements UserDetails {
    @Id
    @GeneratedValue
    @Setter (AccessLevel.NONE)
    private UUID id;

    private String username;

    private String name;

    private String surname;

    private String email;

    private String password;
    @Enumerated (EnumType.STRING)
    private Role role;

    private String avatar;

    @OneToMany (mappedBy = "employee")
    @JsonIgnore
    private List<Device> device;

    public Employee(String username, String name, String surname, String email, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
