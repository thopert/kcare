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
import at.qe.sepm.skeleton.model.Parent;
import at.qe.sepm.skeleton.services.JobService;
import at.qe.sepm.skeleton.services.ParentService;

/**
 * @author Thomas Perteneder
 *
 */
@Component
@Scope("view")
public class JobCreateController implements Serializable {

	private static final long serialVersionUID = 4498112554087041118L;

	@Autowired
	private JobService jobService;
	
	@Autowired
	private ParentService parentService;
	private List<String> parentIds;
	
	private Job job;
	private boolean all;
	
	@PostConstruct
	public void init(){
		this.job = new Job();
		this.job.setDone(false);
		this.all = false;
	}
	
	public String save(){
		if (all) {
			for (Parent p: this.parentService.loadParents()){
				Job newJob = new Job(this.job);
				newJob.setParents(p);
				this.jobService.saveJob(newJob);
			}
			
		} else {
			for (String id: this.parentIds){
				Job newJob = new Job(this.job);
				newJob.setParents(this.parentService.loadParent(Long.parseLong(id)));
				this.jobService.saveJob(newJob);
			}
		}
		
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Job erfolgreich erstellt!"));
		
		return "/secured/supervisor/dataManagement/createJob.xhtml";
	}

	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}
	
	public List<String> getParentIds() {
		return parentIds;
	}

	public void setParentIds(List<String> parentIds) {
		this.parentIds = parentIds;
	}

	public List<Parent> getParents(){
		return this.parentService.loadParents();
	}

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}
	
}
