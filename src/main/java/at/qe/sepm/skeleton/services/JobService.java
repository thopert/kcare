package at.qe.sepm.skeleton.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Job;
import at.qe.sepm.skeleton.model.Parent;
import at.qe.sepm.skeleton.repositories.JobRepository;
import at.qe.sepm.skeleton.repositories.ParentRepository;
import at.qe.sepm.skeleton.utils.LunchReportEntry;

@Component
@Scope("application")
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private ParentRepository parentRepository;
  
    public Job getJobById(long id){
    	return this.jobRepository.getById(id);
    }
    
    public void saveJob(Job job){
    	this.jobRepository.save(job);
    }
    
    public List<Job> getJobs(){
    	return this.jobRepository.findAll();
    }
    
    public List<Job> getUndoneJobsByParent(Long id){
    	return this.jobRepository.getUndoneJobsForParent(id);
    }
    
    public void deleteJob(long id){
    	this.jobRepository.delete(this.getJobById(id));
    }
    
    public void deleteJob(Job job){
    	this.jobRepository.delete(job);
    } 
    
    public Job getLunchPaymentJob(Date deadline, Integer daysBefore){
		Job job = new Job();
		job.setStartDate(deadline);
		job.setEndDate(deadline);
		job.setDaysBefore(daysBefore);
		job.setTitle("Zahlungsaufforderung Mittagessen");
		job.setDone(false);
		return job;
	}

	public void createlunchPayments(List<LunchReportEntry> lunches, Job job, Date start, Date end) {
		for (LunchReportEntry lre : lunches) {
			Parent parent = this.parentRepository.getParentByChild(lre.getChild().getId());
			Job newJob = new Job(job);
			newJob.setParents(parent);
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			newJob.setDescription("Zeitraum: " + sdf.format(start) + " - " + sdf.format(end) + ", Kosten: "
					+ lre.getCosts() + " €" + ", Kind: " + lre.getChild());
			this.jobRepository.save(newJob);
		}
    }

}
