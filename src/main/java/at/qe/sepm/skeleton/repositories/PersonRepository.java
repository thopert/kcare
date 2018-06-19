package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Person;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for managing {@link Person} entities.
 * @author Daniel Jäger(csat2404), Dominik Kuen (csat2284)
 */
public interface PersonRepository extends AbstractRepository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE id = :id")
    Person getById(@Param("id") Long id);

    @Query("SELECT p FROM Person p WHERE mail = :mail")
    Person getByMail(@Param("mail") String mail);
    
    @Query("SELECT p FROM Person p WHERE phone = :phone")
    Person getByPhone(@Param("phone") String phone);

    @Query("SELECT p FROM Person p WHERE businessphone = :businessphone")
    Person getByBusinessphone(@Param("businessphone") String businessphone);

    //Check for supervisor, child, parent, contact
    
}
