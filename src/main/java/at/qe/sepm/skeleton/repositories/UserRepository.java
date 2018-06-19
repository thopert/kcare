package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.UserRole;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for managing {@link User} entities.
 * @author Daniel Jäger (csat2404), Dominik Kuen (csat2284)
 */
public interface UserRepository extends AbstractRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE id = :id")
    User getById(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE username = :username")
    User getByUserName(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE :role MEMBER OF u.roles")
    List<User> getByRole(@Param("role") UserRole role);

    @Query("SELECT ('ADMIN' MEMBER OF u.roles) FROM User u WHERE id = :id")
    Boolean isAdminUser(@Param("id") Long id);

    @Query("SELECT ('SUPERVISOR' MEMBER OF u.roles) FROM User u WHERE id = :id")
    Boolean isSupervisorUser(@Param("id") Long id);

    @Query("SELECT ('PARENTS' MEMBER OF u.roles) FROM User u WHERE id = :id")
    Boolean isParentUser(@Param("id") Long id);

}
