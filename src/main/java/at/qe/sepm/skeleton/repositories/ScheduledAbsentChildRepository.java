package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.ScheduledAbsentChild;
import at.qe.sepm.skeleton.model.Child;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Date;

/**
 * Repository for managing {@link ScheduledAbsentChild} entities.
 * @author Daniel Jäger(csat2404), Dominik Kuen (csat2284)
 */
public interface ScheduledAbsentChildRepository extends AbstractRepository<ScheduledAbsentChild, Long> {

    @Query("SELECT sc FROM ScheduledAbsentChild sc WHERE id = :id")
    ScheduledAbsentChild getById(@Param("id") Long id);

    @Query("SELECT sc FROM ScheduledAbsentChild sc WHERE date = :date")
    List<ScheduledAbsentChild> getForDate(@Param("date") Date date);

	@Query("SELECT absentchild FROM ScheduledAbsentChild WHERE date = :date")
    List<Child> getChildrenForDate(@Param("date") Date date);

    @Query("SELECT sac FROM ScheduledAbsentChild sac WHERE date = :date AND absentchild IN (SELECT id FROM Child WHERE id = :id)")
    ScheduledAbsentChild getForAbsentChildAtDate(@Param("date") Date date, @Param("id") Long id);

}
