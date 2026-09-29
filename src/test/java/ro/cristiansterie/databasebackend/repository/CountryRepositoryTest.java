package ro.cristiansterie.databasebackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.CountryEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CountryRepositoryTest extends AbstractCrudRepositoryTest<CountryEntity> {
	@Autowired
	private CountryRepository countryRepository;

	@Override
	protected JpaRepository<CountryEntity, UUID> repository() {
		return countryRepository;
	}

	@Override
	protected CountryEntity newEntity() {
		CountryEntity country = new CountryEntity();
		country.setName("Repository test " + UUID.randomUUID());
		country.setContinent("Europe");
		return country;
	}

	@Override
	protected CountryEntity updateEntity(CountryEntity country) {
		country.setContinent("Asia");
		return country;
	}

	@Override
	protected UUID entityId(CountryEntity country) {
		return country.getId();
	}

	@Override
	protected void assertUpdated(CountryEntity country) {
		assertThat(country.getContinent()).isEqualTo("Asia");
	}
}
