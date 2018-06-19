package at.qe.sepm.skeleton.repositories;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.ScheduledChild;

/**
 * Repository for managing {@link ScheduledChild} entities.
 * @author Daniel Jäger(csat2404), Dominik Kuen (csat2284)
 */
public interface ScheduledChildRepository extends AbstractRepository<ScheduledChild, Long> {

    @Query("SELECT sc FROM ScheduledChild sc WHERE id = :id")
    ScheduledChild getById(@Param("id") Long id);

    @Query("SELECT sc FROM ScheduledChild sc WHERE date = :date")
    List<ScheduledChild> getForDate(@Param("date") Date date);

    @Query("SELECT child FROM ScheduledChild WHERE date = :date")
    List<Child> getChildrenForDate(@Param("date") Date date);

    @Query("SELECT sc FROM ScheduledChild sc WHERE date = :date AND child IN (SELECT id FROM Child WHERE id = :id)")
    ScheduledChild getForChildAtDate(@Param("date") Date date, @Param("id") Long id);
    
    @Query("SELECT child FROM ScheduledChild WHERE date >= :start AND date <= :end")
    Set<Child> getByInterval(@Param("start") Date startDate, @Param("end") Date endDate);
}
