package ro.cristiansterie.databasebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import ro.cristiansterie.databasebackend.util.enums.CollectionItemConditionType;

import java.util.UUID;

@Entity
@Table(name = "coins")
@Getter
@Setter
@NoArgsConstructor
public class CoinEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@UuidGenerator
	private UUID id;
	private UUID collectionId;
	private UUID materialId;
	private UUID denominationId;
	private CollectionItemConditionType condition;
	@Column(name = "year_created")
	private Integer year;
	private Double diameter;
	private String description;
	private String link;
	private Integer numericValue;
	private Integer extraYear;
	private Integer units;
}
