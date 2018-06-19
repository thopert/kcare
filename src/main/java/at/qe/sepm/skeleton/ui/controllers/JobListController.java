package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Job;
import at.qe.sepm.skeleton.services.JobService;
import at.qe.sepm.skeleton.ui.beans.ParentInfoBean;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;

@Component
@Scope("view")
public class JobListController implements Serializable {
	private static final long serialVersionUID = -7799867982047750982L;

	@Autowired
	private JobService jobService;
	private List<Job> jobs;
	private List<Job> filteredJobs;
	
	@Autowired
	private SessionInfoBean sessionInfoBean;
	@Autowired
	private ParentInfoBean parentInfoBean;
	
	@PostConstruct
	public void init(){
		if (this.sessionInfoBean.hasRole("PARENTS"))
			this.jobs = this.jobService.getUndoneJobsByParent(this.parentInfoBean.getCurrentParent().getId());
		else
			this.jobs = this.jobService.getJobs();
	}
	
	public List<Job> getJobs(){
		return this.jobs;
	}
	
	public void setDone(Job job){
		this.jobService.saveJob(job);
		
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Job-Status erfolgreich bearbeitet!"));
	}

	public List<Job> getFilteredJobs() {
		return filteredJobs;
	}

	public void setFilteredJobs(List<Job> filteredJobs) {
		this.filteredJobs = filteredJobs;
	}
	
}
