package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Message;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Date;

/**
 * Repository for managing {@link Message} entities.
 * @author Daniel Jäger(csat2404), Dominik Kuen (csat2284)
 */
public interface MessageRepository extends AbstractRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE id = :id")
    Message getById(@Param("id") Long id);

    @Query("SELECT m FROM Message m WHERE datetime = :date")
    List<Message> getByDate(@Param("date") Date date);

    // Author is a Person
    @Query("SELECT m FROM Message m WHERE author IN (SELECT id FROM Person WHERE id = :id)")
    List<Message> getByAuthor(@Param("id") Long id);
}