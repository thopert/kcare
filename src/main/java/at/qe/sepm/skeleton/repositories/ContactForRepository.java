package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.ContactFor;
import at.qe.sepm.skeleton.model.Child;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for managing {@link Contact} entities.
 * @author Daniel Jäger(csat2404), Dominik Kuen (csat2284)
 */
public interface ContactForRepository extends AbstractRepository<ContactFor, Long> {

    @Query("SELECT c FROM ContactFor c WHERE contact IN (SELECT id FROM Contact WHERE id = :coid) AND child IN (SELECT id FROM Child WHERE id = :chid)")
    ContactFor getByContactAndChild(@Param("coid") Long contact, @Param("chid") Long child);

}
