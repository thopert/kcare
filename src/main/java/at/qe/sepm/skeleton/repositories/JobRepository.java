package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Job;
import at.qe.sepm.skeleton.model.Parent;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Date;

/**
 * Repository for managing {@link Job} entities.
 * @author Daniel Jäger(csat2404), Dominik Kuen (csat2284)
 */
public interface JobRepository extends AbstractRepository<Job, Long> {

    @Query("SELECT j FROM Job j WHERE id = :id")
    Job getById(@Param("id") Long id);

    @Query("SELECT j FROM Job j WHERE startdate >= :date AND enddate <= :date")
    List<Job> getByDate(@Param("date") Date date);

    @Query("SELECT j FROM Job j WHERE startdate = :date")
    List<Job> getByStartDate(@Param("date") Date date);

    @Query("SELECT j FROM Job j WHERE enddate = :date")
    List<Job> getByEndDate(@Param("date") Date date);

    @Query("SELECT j FROM Job j WHERE startdate >= :date AND enddate <= :date AND done = FALSE")
    List<Job> getUndoneJobsForDate(@Param("date") Date date);

    @Query("SELECT j FROM Job j WHERE startdate >= :date AND enddate <= :date AND done = TRUE")
    List<Job> getDoneJobsForDate(@Param("date") Date date);

    @Query("SELECT j FROM Job j WHERE done = TRUE AND parents IN (SELECT id FROM Parent WHERE id = :id)")
    List<Job> getDoneJobsForParent(@Param("id") Long id);
    
    @Query("SELECT j FROM Job j WHERE done = FALSE AND parents IN (SELECT id FROM Parent WHERE id = :id)")
    List<Job> getUndoneJobsForParent(@Param("id") Long id);

    @Query("SELECT j FROM Job j WHERE done = FALSE AND parents IS NOT NULL AND (startdate - daysbefore) = :date")
    List<Job> getJobsToSendInfoMail(@Param("date") Date date);

}
