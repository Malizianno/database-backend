package ro.cristiansterie.databasebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.UUID;

@Entity
@Table(name = "user_roles")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@UuidGenerator
	private UUID id;

	@Column(nullable = false, unique = true)
	private String name;

	private String description;

	public RoleEntity(UUID id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public GrantedAuthority parseToGrantedAuthority() {
		return new SimpleGrantedAuthority(this.name);
	}

}