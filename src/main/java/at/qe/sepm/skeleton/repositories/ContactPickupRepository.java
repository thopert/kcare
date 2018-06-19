package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.ContactPickup;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Date;

/**
 * Repository for managing {@link ContactPickup} entities.
 * @author Daniel Jäger(csat2404), Dominik Kuen (csat2284)
 */
public interface ContactPickupRepository extends AbstractRepository<ContactPickup, Long> {

    @Query("SELECT c FROM ContactPickup c WHERE id = :id")
    ContactPickup getById(@Param("id") Long id);

    @Query("SELECT c FROM ContactPickup c WHERE date = :date")
    List<ContactPickup> getByDate(@Param("date") Date date);

    @Query("SELECT c FROM ContactPickup c WHERE child IN (SELECT id FROM Child WHERE id = :id)")
    List<ContactPickup> getByChild(@Param("id") Long id);

    @Query("SELECT c FROM ContactPickup c WHERE contact IN (SELECT id FROM Contact WHERE id = :id)")
    List<ContactPickup> getByContact(@Param("id") Long id);

    @Query("SELECT c FROM ContactPickup c WHERE date = :date AND contact IN (SELECT id FROM Contact WHERE id = :contactid) AND child IN (SELECT id FROM Child WHERE id = :childid)")
    ContactPickup getByDateContactChild(@Param("date") Date date, @Param("contactid") Long contactid, @Param("childid") Long childid);

    @Query("SELECT c FROM ContactPickup c WHERE DAY(date) = DAY(:date) AND MONTH(date) = MONTH(:date) AND YEAR(date) = YEAR(:date) AND child IN (SELECT id FROM Child WHERE id = :childid)")
    ContactPickup getByDateChild(@Param("date") Date date, @Param("childid") Long childid);

}
