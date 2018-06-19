package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Contact;
import at.qe.sepm.skeleton.model.Child;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for managing {@link Contact} entities.
 * @author Daniel Jäger(csat2404), Dominik Kuen (csat2284)
 */
public interface ContactRepository extends AbstractRepository<Contact, Long> {

    @Query("SELECT c FROM Contact c WHERE id = :id")
    Contact getById(@Param("id") Long id);

    @Query("SELECT contact FROM ContactFor WHERE child IN (SELECT id FROM Child WHERE id = :id)")
    List<Contact> getContactsByChild(@Param("id") Long id);

    @Query("SELECT child FROM ContactFor WHERE contact IN (SELECT id FROM Contact WHERE id = :id)")
    List<Child> getChildrenByContact(@Param("id") Long id);

}
