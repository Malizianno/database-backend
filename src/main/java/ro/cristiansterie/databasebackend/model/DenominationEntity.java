package ro.cristiansterie.databasebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "denominations")
@Getter
@Setter
@NoArgsConstructor
public class DenominationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@UuidGenerator
	@Column(name = "id")
	private UUID id;
	private UUID countryId;
	private String title;
}
