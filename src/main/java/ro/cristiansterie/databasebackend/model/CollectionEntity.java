package ro.cristiansterie.databasebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.cristiansterie.databasebackend.util.enums.CollectionType;

@Entity
@Table(name = "collections")
@Getter
@Setter
@NoArgsConstructor
public class CollectionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private Long userId;
	private CollectionType type;
}
