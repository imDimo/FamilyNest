package edu.uwgb.se372.familynest.event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import edu.uwgb.se372.familynest.user.NestUser;
import edu.uwgb.se372.familynest.user.NestUserRepository;

@SpringBootTest
@TestPropertySource(properties = {
	"spring.datasource.url=jdbc:h2:mem:testdb2;MODE=MySQL;NON_KEYWORDS=USER;DB_CLOSE_DELAY=-1",
	"spring.datasource.driverClassName=org.h2.Driver",
	"spring.datasource.username=sa",
	"spring.datasource.password=",
	"spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
	"spring.jpa.hibernate.ddl-auto=update",
	"spring.jpa.properties.hibernate.globally_quoted_identifiers=true",
	"spring.session.store-type=none",
	"spring.docker.compose.enabled=false",
	"nest.data.setup.enabled=false"
})
class CalendarEventManagementServiceMonthYearTests {
	
	@Autowired
	private CalendarEventManagementService calendarEventService;
	
	@Autowired
	private CalendarEventRepository calendarEventRepository;
	
	@Autowired
	private NestUserRepository userRepository;
	
	@Test
	void getEventsByMonthYear_filtersByEventDate() {
		NestUser creator = new NestUser();
		creator.setUsername("creator1");
		creator.setPassword("pw");
		creator = userRepository.save(creator);
		
		CalendarEventManagement janEvent = new CalendarEventManagement();
		janEvent.setTitle("Jan event");
		janEvent.setDescription("d");
		janEvent.setCreator(creator);
		janEvent.setEventDate(LocalDateTime.of(2026, 1, 15, 10, 0));
		calendarEventRepository.save(janEvent);
		
		CalendarEventManagement febEvent = new CalendarEventManagement();
		febEvent.setTitle("Feb event");
		febEvent.setDescription("d");
		febEvent.setCreator(creator);
		febEvent.setEventDate(LocalDateTime.of(2026, 2, 1, 0, 0));
		calendarEventRepository.save(febEvent);
		
		List<CalendarEventManagement> jan = calendarEventService.getEventsByMonthYear(1, 2026);
		assertEquals(1, jan.size());
		assertEquals("Jan event", jan.getFirst().getTitle());
	}
}

