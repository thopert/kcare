package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Supervisor;
import at.qe.sepm.skeleton.services.SupervisorService;

@Component
@Scope ("session")
public class SupervisorDetailController implements Serializable {
	private static final long serialVersionUID = -3347759335377124003L;
	
	@Autowired
	private SupervisorService supervisorService;
	private Supervisor supervisor;
	
	public String save(){
		this.supervisorService.saveSupervisor(this.supervisor);
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", this.supervisor + " erfolgreich modifiziert!"));
		return "/admin/editSupervisor.xhtml";
	}
	
	public String delete(){
		this.supervisorService.deleteSupervisor(this.supervisor);
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", this.supervisor + " erfolgreich gelöscht!"));
		return "/admin/listSupervisors.xhtml";
	}
	
	public Supervisor getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}
	
	public void handleFileUpload(FileUploadEvent event){
		this.supervisor.getPerson().setPicture(event.getFile().getContents());
	}
}
