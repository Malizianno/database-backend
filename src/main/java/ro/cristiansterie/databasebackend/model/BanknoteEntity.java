package ro.cristiansterie.databasebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.cristiansterie.databasebackend.util.enums.CollectionItemConditionType;

@Entity
@Table(name = "banknotes")
@Getter
@Setter
@NoArgsConstructor
public class BanknoteEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long collectionId;
	private Long materialId;
	private Long denominationId;
	private CollectionItemConditionType condition;
	@Column(name = "year_created")
	private Integer year;
	private Double length;
	private Double thickness;
	private Double width;
	private String description;
	private String link;
	private Integer numericValue;
	private Integer extraYear;
	private Integer units;
}
