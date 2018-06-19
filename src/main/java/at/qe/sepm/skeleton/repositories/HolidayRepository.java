package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Holiday;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Date;

/**
 * Repository for managing {@link Holiday} entities.
 * @author Daniel Jäger(csat2404), Dominik Kuen (csat2284)
 */
public interface HolidayRepository extends AbstractRepository<Holiday, Long> {

    @Query("SELECT h FROM Holiday h WHERE id = :id")
	Holiday getById(@Param("id") Long id);

	@Query("SELECT h FROM Holiday h WHERE fromdate = :date")
	Holiday getHolidayByDate(@Param("date") Date date);

    @Query("SELECT h FROM Holiday h WHERE MONTH(fromdate) = :month AND YEAR(fromdate) = :year")
    List<Holiday> getHolidaysForMonth(@Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT h FROM Holiday h WHERE YEAR(fromdate) = :year")
    List<Holiday> getHolidaysForYear(@Param("year") Integer year);

    @Query("SELECT COUNT(*) > 0 FROM Holiday WHERE :date >= fromdate AND :date <= todate")
    Boolean isDateInHolidays(@Param("date") Date date);

}
