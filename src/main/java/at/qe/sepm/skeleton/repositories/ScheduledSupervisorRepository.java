package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.ScheduledSupervisor;
import at.qe.sepm.skeleton.model.Supervisor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Date;

/**
 * Repository for managing {@link ScheduledSupervisor} entities.
 * @author Daniel Jäger(csat2404), Dominik Kuen (csat2284)
 */
public interface ScheduledSupervisorRepository extends AbstractRepository<ScheduledSupervisor, Long> {

    @Query("SELECT ss FROM ScheduledSupervisor ss WHERE id = :id")
    ScheduledSupervisor getById(@Param("id") Long id);

    @Query("SELECT ss FROM ScheduledSupervisor ss WHERE date = :date")
    List<ScheduledSupervisor> getForDate(@Param("date") Date date);

    @Query("SELECT supervisor FROM ScheduledSupervisor WHERE date = :date")
    List<Supervisor> getSupervisorsForDate(@Param("date") Date date);

    @Query("SELECT s FROM ScheduledSupervisor s WHERE date = :date AND supervisor IN (SELECT id FROM Supervisor WHERE id = :id)")
    ScheduledSupervisor getForSupervisorAtDate(@Param("date") Date date, @Param("id") Long id);

}
