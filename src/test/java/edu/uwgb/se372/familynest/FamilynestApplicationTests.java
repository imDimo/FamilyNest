package edu.uwgb.se372.familynest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
	"spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;NON_KEYWORDS=USER;DB_CLOSE_DELAY=-1",
	"spring.datasource.driverClassName=org.h2.Driver",
	"spring.datasource.username=sa",
	"spring.datasource.password=",
	"spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
	"spring.jpa.hibernate.ddl-auto=update",
	"spring.jpa.properties.hibernate.globally_quoted_identifiers=true",
	"nest.data.setup.enabled=false"
})
class FamilynestApplicationTests {

	@Test
	void contextLoads() {
		// Simple test to verify the application context loads
		assertTrue(true);
	}

}
