package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Contact;
import at.qe.sepm.skeleton.services.ContactService;
import at.qe.sepm.skeleton.ui.beans.ParentInfoBean;
import at.qe.sepm.skeleton.utils.DateUtils;

/**
 * @author Thomas Perteneder
 *
 */
@Component
@Scope ("view")
public class ParentSetPickupController implements Serializable {
	
	private static final long serialVersionUID = 7848180124109398323L;

	@Autowired
	private ParentInfoBean parentInfoBean;
	
	@Autowired
	private ContactService contactService;

	private List<Child> children;
	private Set<Contact> contacts;
	
	private Long childId;
	private Long contactId;
	private Date date;
	
	@PostConstruct
	public void init(){
		this.date = DateUtils.currentSimpleDate();
		this.children = this.parentInfoBean.getCurrentChildren();
		this.childId = this.children.get(0).getId();
		this.contacts = this.contactService.loadContactsByChildren(this.children);
	}
	
	public void save(){
		this.contactService.setPickup(this.contactId, this.childId, this.date);
		
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Abholperson erfolgreich zugeteilt!"));
	}
	
	public void delete(){
		this.contactService.deletePickup(this.childId, this.date);
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Abholperson erfolgreich entfernt!"));
	}
	
	public Contact getPickup(){
		return this.contactService.getPickup(this.childId, this.date);
	}
	
	public List<Child> getChildren(){
		return this.children;
	}
	
	public Set<Contact> getContacts(){
		return this.contacts;
	}

	public Long getChildId() {
		return childId;
	}

	public Long getContactId() {
		return contactId;
	}

	public Date getDate() {
		return date;
	}

	public void setChildId(Long childId) {
		this.childId = childId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
