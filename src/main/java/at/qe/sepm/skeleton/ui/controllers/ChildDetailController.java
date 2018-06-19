package at.qe.sepm.skeleton.ui.controllers;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Contact;
import at.qe.sepm.skeleton.model.Parent;
import at.qe.sepm.skeleton.services.ChildService;
import at.qe.sepm.skeleton.services.ContactService;
import at.qe.sepm.skeleton.services.ParentService;

/**
 * @author Thomas Perteneder
 *
 */
@Component
@Scope("session")
public class ChildDetailController {
	@Autowired
	private ChildService childService;
	private Child child;
	
	@Autowired
	private ParentService parentService;
	private Long parentId;
	
	@Autowired
	private ContactService contactService;
	
	public String saveChild(){
		if (this.parentId != null){
			this.child.setParents(this.parentService.loadParent(this.parentId));
		}
		
		this.childService.saveChild(this.child);
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", this.child + " erfolgreich modifiziert!"));
		return "/secured/supervisor/dataManagement/editChild.xhtml";
	}
	
	public String deleteChild(){
		this.childService.deleteChild(this.child);
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", this.child + " erfolgreich gelöscht!"));
		return "/secured/supervisor/dataManagement/listChildren.xhtml";
	}
	
	public Child getChild() {
		return child;
	}
	
	public void setChild(Child child) {
		this.child = child;
		this.parentId = null;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<Parent> getParents(){
		List<Parent> parents = this.parentService.loadParents();
		parents.remove(this.child.getParents());
		return parents;
	}
	
	public List<Contact> getContacts(){
		return this.contactService.loadContactsByChild(this.child.getId());
	}
	
	public void handleFileUpload(FileUploadEvent event){
		this.child.getPerson().setPicture(event.getFile().getContents());
	}
}
