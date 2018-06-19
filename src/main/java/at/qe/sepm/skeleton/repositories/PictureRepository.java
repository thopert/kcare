package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Picture;
import at.qe.sepm.skeleton.model.Person;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Date;

/**
 * Repository for managing {@link Picture} entities.
 * @author Daniel Jäger(csat2404), Dominik Kuen (csat2284)
 */
public interface PictureRepository extends AbstractRepository<Picture, Long> {

    @Query("SELECT p FROM Picture p WHERE id = :id")
    Picture getById(@Param("id") Long id);

	@Query("SELECT p FROM Picture p WHERE person = :person")
	List<Picture> getPicturesByPerson(@Param("person") Person person);

}
