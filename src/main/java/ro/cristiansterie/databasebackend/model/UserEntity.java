package ro.cristiansterie.databasebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles_mapping",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    public UserEntity(String username, String password, String email, Set<RoleEntity> roles) {
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setRoles(roles);
    }

	// parse roles as SimpleGrantedAuthority and return java.util.List of them
    public List<GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        this.roles.stream()
                .filter(Objects::nonNull)
                .map(RoleEntity::parseToGrantedAuthority)
                .forEach(authorities::add);

        return authorities;
    }
}