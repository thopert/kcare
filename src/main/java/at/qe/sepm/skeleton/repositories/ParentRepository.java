package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.Parent;
import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Person;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for managing {@link Parent} entities.
 * @author Daniel Jäger (csat2404), Dominik Kuen (csat2284)
 */
public interface ParentRepository extends AbstractRepository<Parent, Long> {

	@Query("SELECT p FROM Parent p WHERE id = :id")
    Parent getById(@Param("id") Long id);

	@Query("SELECT u FROM User u WHERE id IN (SELECT user FROM Parent WHERE id = :id)")
	User getUserByParent(@Param("id") Long id);

	@Query("SELECT c FROM Child c WHERE parents IN (SELECT id FROM Parent WHERE id = :id)")
	List<Child> getChildrenByParent(@Param("id") Long id);

	@Query("SELECT p FROM Parent p WHERE mother IN (SELECT id FROM Person WHERE id = :id) OR father IN (SELECT id FROM Person WHERE id = :id)")
	Parent getByMotherOrFather(@Param("id") Long id);

	@Query("SELECT p FROM Parent p WHERE user IN (SELECT id FROM User WHERE id = :id)")
	Parent getByUser(@Param("id") Long id);
	
	@Query("SELECT p FROM Parent p WHERE active = TRUE")
	List<Parent> getActiveParents();
	
	@Query ("SELECT p FROM Parent p WHERE id NOT IN (SELECT parents FROM Child)")
	List<Parent> getParentsNoChild();
	
	@Query("SELECT p FROM Parent p WHERE id IN (SELECT parents FROM Child WHERE id = :id)")
	Parent getParentByChild(@Param("id") Long childId);
	
	@Query("SELECT p FROM Parent p WHERE id IN (SELECT parents FROM Job WHERE id = :id)")
	Parent getParentByJob(@Param("id") Long jobId);

}
