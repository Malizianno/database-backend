package ro.cristiansterie.databasebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import ro.cristiansterie.databasebackend.util.enums.CollectionType;

import java.util.UUID;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
public class ImageEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@UuidGenerator
    private UUID id;
	private UUID itemId;
	private CollectionType itemType;
	private String imageUrl;
}
