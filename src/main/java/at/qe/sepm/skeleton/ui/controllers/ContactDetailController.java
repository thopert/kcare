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
import at.qe.sepm.skeleton.model.Contact;
import at.qe.sepm.skeleton.services.ContactService;

/**
 * @author Thomas Perteneder
 *
 */
@Component
@Scope("session")
public class ContactDetailController implements Serializable {
	private static final long serialVersionUID = -1079567603475585870L;
	
	@Autowired
	private ContactService contactService;
	private Contact contact;
	
	public String saveContact(){
		this.contactService.saveContact(this.contact);
		
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", this.contact + " erfolgreich modifiziert!"));
		return "/secured/supervisor/dataManagement/editContact.xhtml";
	}
	
	public String delete(){
		this.contactService.deleteContact(this.contact);
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", this.contact + " wurde erfolgreich gelöscht!"));
		return "/secured/supervisor/dataManagement/listContacts.xhtml";
	}

	public List<Child> getChildren(){
		return this.contactService.loadChildrenByContact(this.contact.getId());
	}
	
	public Contact getContact() {
		return this.contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	public void handleFileUpload(FileUploadEvent event){
		this.contact.getPerson().setPicture(event.getFile().getContents());
	}
}
