package edu.uwgb.se372.familynest.event;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.uwgb.se372.familynest.user.NestUser;

@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEventManagement, Long> {
	
	public List<CalendarEventManagement> findByCreator(NestUser creator);
	
	@Query("SELECT e FROM CalendarEventManagement e JOIN e.members m WHERE m = :member")
	public List<CalendarEventManagement> findEventsByMember(@Param("member") NestUser member);
	
	@Query("SELECT e FROM CalendarEventManagement e WHERE e.creator = :creator OR :member MEMBER OF e.members")
	public List<CalendarEventManagement> findEventsByCreatorOrMember(@Param("creator") NestUser creator, @Param("member") NestUser member);
	
	@Query("SELECT e FROM CalendarEventManagement e WHERE e.eventDate >= :startInclusive AND e.eventDate < :endExclusive ORDER BY e.eventDate ASC")
	public List<CalendarEventManagement> findEventsByEventDateRange(@Param("startInclusive") LocalDateTime startInclusive,
																	@Param("endExclusive") LocalDateTime endExclusive);
}
