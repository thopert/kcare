package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Contact;
import at.qe.sepm.skeleton.services.ContactService;
import at.qe.sepm.skeleton.ui.beans.ParentInfoBean;

@Component
@Scope ("view")
public class ContactCreateController implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ContactService contactService;
	@Autowired
	private ParentInfoBean parentInfoBean;
	private Contact contact;
	
	@PostConstruct
	public void init(){
		this.contact = this.contactService.getEmptyContact();
	}

	public String saveContact(){
		this.contactService.saveContact(this.contact);
		for (Child child: this.parentInfoBean.getCurrentChildren())
			this.contactService.addChildToContact(this.contact, child);
		
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Bezugsperson erfolgreich erstellt!"));
		return "/secured/parent/createContact.xhtml";
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	public void handleFileUpload(FileUploadEvent event){
		this.contact.getPerson().setPicture(event.getFile().getContents());
	}
}
