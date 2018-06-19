package at.qe.sepm.skeleton.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Parent;
import at.qe.sepm.skeleton.model.Person;

/**
 * Repository for managing {@link Child} entities.
 * @author Daniel Jäger (csat2404), Dominik Kuen (csat2284)
 */
public interface ChildRepository extends AbstractRepository<Child, Long> {

    @Query("SELECT c FROM Child c WHERE id = :id")
    Child getById(@Param("id") Long id);

    @Query("SELECT c FROM Child c WHERE c.person IN (SELECT id FROM Person WHERE firstname = :firstname AND lastname = :lastname)")
    List<Child> getChildrenByName(@Param("firstname") String firstname, @Param("lastname") String lastname);

    @Query("SELECT c FROM Child c WHERE c.parents IN (SELECT id FROM Parent WHERE id = :id)")
    List<Child> getChildrenByParents(@Param("id") Long id);

    @Query("SELECT person FROM Child c WHERE c.id = :id")
    Person getPersonOfChild(@Param("id") Long id);

    @Query("SELECT parents FROM Child c WHERE c.id = :id")
    Parent getParentsOfChild(@Param("id") Long id);

    @Query("SELECT c FROM Child c WHERE c.parents = null")
    List<Child> getChildrenNoParents();
    
    @Query("SELECT c FROM Child c WHERE c.person.birthdate = :date AND c.id in (SELECT child FROM ScheduledChild WHERE date = :date)")
    List<Child> getBirthDayChildren(@Param ("date") Date date);

    @Query("SELECT c FROM Child c WHERE DAY(c.person.birthdate) > DAY(:startdate) AND DAY(c.person.birthdate) < DAY(:enddate) AND MONTH(c.person.birthdate) > MONTH(:startdate) AND MONTH(c.person.birthdate) < MONTH(:enddate)")
    List<Child> getBirthdayChildrenWithinDates(@Param("startdate") Date startdate, @Param("enddate") Date enddate);

    @Query("SELECT c FROM Child c WHERE c.cancellationdate < :date")
    List<Child> getChildrenWithLowerCancellationDate(@Param("date") Date date);

}

