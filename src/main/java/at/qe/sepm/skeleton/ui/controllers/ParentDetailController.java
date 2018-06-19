package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Parent;
import at.qe.sepm.skeleton.services.ParentService;


/**
 * @author Thomas Perteneder
 *
 */
@Component
@Scope ("session")
public class ParentDetailController implements Serializable{
	private static final long serialVersionUID = 8436292035895386565L;
	
	@Autowired
	private ParentService parentService;
	private Parent parent;
	
	public String saveParent(){
		this.parentService.saveParent(this.parent);
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Personaldaten erfolgreich modifiziert!"));
		
		return "/secured/supervisor/dataManagement/editParent.xhtml";
	}
	
	public String deleteParent(){
		this.parentService.deleteParent(this.parent);
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", this.parent + " erfolgreich gelöscht!"));
		return "/secured/supervisor/dataManagement/listParents.xhtml";
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}
	
	public void uploadMother(FileUploadEvent event){
		this.parent.getMother().setPicture(event.getFile().getContents());
	}

	public void uploadFather(FileUploadEvent event){
		this.parent.getFather().setPicture(event.getFile().getContents());
	}
	
	public List<Child> getChildren(){
		return this.parentService.loadChildrenByParent(this.parent);
	}
}
