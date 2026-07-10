package ro.cristiansterie.databasebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("ro.cristiansterie.databasebackend.model")
@EnableJpaRepositories("ro.cristiansterie.databasebackend.repository")
@ConfigurationPropertiesScan
public class DatabaseBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseBackendApplication.class, args);
	}

}
