package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Supervisor;
import at.qe.sepm.skeleton.services.SupervisorService;

/**
 * @author Thomas Perteneder, (a bit) Dominik Kuen (csat2284)
 *
 */
@Component
@Scope("view")
public class SupervisorCreateController implements Serializable {

	private static final long serialVersionUID = -7173824030207747698L;

	@Autowired
	private SupervisorService supervisorService;
	
	private Supervisor supervisor;

	@PostConstruct
	public void init() {
		supervisor = this.supervisorService.getEmptySupervisor();
	}

	public String createSupervisor() {
		supervisorService.saveNewSupervisor(supervisor);
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Betreuer/in erfolgreich erstellt!"));
		return "/admin/createSupervisor.xhtml";
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
