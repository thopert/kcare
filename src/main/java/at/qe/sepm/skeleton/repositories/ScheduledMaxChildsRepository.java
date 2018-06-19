package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.ScheduledMaxChilds;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Date;

/**
 * Repository for managing {@link ScheduledMaxChilds} entities.
 * @author Daniel Jäger(csat2404), Dominik Kuen (csat2284)
 */
public interface ScheduledMaxChildsRepository extends AbstractRepository<ScheduledMaxChilds, Long> {

    @Query("SELECT smc FROM ScheduledMaxChilds smc WHERE id = :id")
    ScheduledMaxChilds getById(@Param("id") Long id);

    @Query("SELECT smc FROM ScheduledMaxChilds smc WHERE date = :date")
    ScheduledMaxChilds getForDate(@Param("date") Date date);

    @Query("SELECT childslimit FROM ScheduledMaxChilds WHERE date = :date")
    Long getMaxChildrenForDate(@Param("date") Date date);

}
