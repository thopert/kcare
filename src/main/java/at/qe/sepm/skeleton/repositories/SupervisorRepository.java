package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.Supervisor;
import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Person;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for managing {@link Supervisor} entities.
 * @author Daniel Jäger (csat2404), Dominik Kuen (csat2284)
 */
public interface SupervisorRepository extends AbstractRepository<Supervisor, Long> {

    @Query("SELECT s FROM Supervisor s WHERE id = :id")
    Supervisor getById(@Param("id") Long id);

    @Query("SELECT s FROM Supervisor s WHERE user IN (SELECT id FROM User WHERE id = :id)")
    Supervisor getByUser(@Param("id") Long id);

    @Query("SELECT s FROM Supervisor s WHERE user IN (SELECT id FROM User WHERE username = :username)")
    Supervisor getByUserName(@Param("username") String name);
    
    @Query("SELECT s FROM Supervisor s WHERE s.person IN (SELECT id FROM Person WHERE firstname = :firstname AND lastname = :lastname)")
    List<Supervisor> getSupervisorsByName(@Param("firstname") String firstname, @Param("lastname") String lastname);

}
