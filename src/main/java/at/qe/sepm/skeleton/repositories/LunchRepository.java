package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Lunch;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Date;

/**
 * Repository for managing {@link Lunch} entities.
 * @author Daniel Jäger(csat2404), Dominik Kuen (csat2284)
 */
public interface LunchRepository extends AbstractRepository<Lunch, Long> {

    @Query("SELECT l FROM Lunch l WHERE id = :id")
    Lunch getById(@Param("id") Long id);

    @Query("SELECT l FROM Lunch l WHERE date = :date")
    List<Lunch> getByDate(@Param("date") Date date);

    @Query("SELECT l FROM Lunch l WHERE child IN (SELECT id FROM Child WHERE id = :id)")
    List<Lunch> getByChild(@Param("id") Long id);

    @Query("SELECT l FROM Lunch l WHERE date = :date AND child in (SELECT id FROM Child WHERE id = :id)")
    Lunch getByDateChild(@Param("date") Date date, @Param("id") Long id);
    
    @Query("SELECT l from Lunch l WHERE date >= :start AND date <= :end AND child in (SELECT id FROM Child WHERE id = :id)")
    List<Lunch> getByIntervalChild(@Param("start") Date start, @Param("end") Date end, @Param("id") Long childId);

}