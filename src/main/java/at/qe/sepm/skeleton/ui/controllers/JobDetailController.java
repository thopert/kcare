package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Job;
import at.qe.sepm.skeleton.model.Parent;
import at.qe.sepm.skeleton.services.JobService;
import at.qe.sepm.skeleton.services.ParentService;

@Component
@Scope("session")
public class JobDetailController implements Serializable {
	private static final long serialVersionUID = -8072078056087197587L;

	@Autowired
	private JobService jobService;
	@Autowired
	private ParentService parentService;

	private Long parentId;
	private Job job;

	public void save() {
		this.job.setParents(this.parentService.loadParent(this.parentId));

		this.jobService.saveJob(this.job);
		FacesContext.getCurrentInstance().addMessage("success", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:",
				"Job: " + this.job.getTitle() + " erfolgreich bearbeitet!"));
	}

	public String delete() {
		this.jobService.deleteJob(this.job);
		FacesContext.getCurrentInstance().addMessage("success", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:",
				"Job: " + this.job.getTitle() + " erfolgreich gelöscht!"));

		return "/secured/supervisor/dataManagement/listJobs.xhtml";
	}

	public Parent getParent() {
		return this.parentService.getParentByJob(this.job.getId());
	}

	public List<Parent> getParents() {
		List<Parent> parents = this.parentService.loadParents();
		if (parents != null)
			parents.remove(this.getParent());

		return parents;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
