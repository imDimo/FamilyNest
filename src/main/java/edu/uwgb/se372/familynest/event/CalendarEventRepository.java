package edu.uwgb.se372.familynest.event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.uwgb.se372.familynest.user.NestUser;

@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEventManagement, Long> {
	
	List<CalendarEventManagement> findByCreator(NestUser creator);
	
	@Query("SELECT e FROM CalendarEventManagement e JOIN e.members m WHERE m = :member")
	List<CalendarEventManagement> findEventsByMember(@Param("member") NestUser member);
	
	@Query("SELECT e FROM CalendarEventManagement e WHERE e.creator = :creator OR :member MEMBER OF e.members")
	List<CalendarEventManagement> findEventsByCreatorOrMember(@Param("creator") NestUser creator, @Param("member") NestUser member);
}
