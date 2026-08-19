package ro.cristiansterie.databasebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "domains")
@Getter
@Setter
@NoArgsConstructor
public class DomainEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@UuidGenerator
	@Column(name = "id")
	private UUID id;
	private String name;
	private String description;
}
