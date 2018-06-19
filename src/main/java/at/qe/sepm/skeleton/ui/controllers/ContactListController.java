package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Contact;
import at.qe.sepm.skeleton.services.ContactService;
import at.qe.sepm.skeleton.ui.beans.ParentInfoBean;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;

/**
 * @author Thomas Perteneder
 *
 */
@Component
@Scope("view")
public class ContactListController implements Serializable{
	private static final long serialVersionUID = 5154064567707111731L;

	@Autowired
	private ContactService contactService;

	@Autowired
	private SessionInfoBean sessionInfoBean;

	@Autowired
	private ParentInfoBean parentInfoBean;
	
	private List<Contact> contacts;
	
	private List<Contact> filteredContacts;
	
	@PostConstruct
	public void init(){
		if (this.sessionInfoBean.hasRole("PARENTS")){
			this.contacts = new ArrayList<Contact>();
			contacts.addAll(contactService.loadContactsByChildren(this.parentInfoBean.getCurrentChildren()));
		} else
			this.contacts = this.contactService.loadContacts();
			
	}

	public List<Contact> getContacts() {
		return this.contacts;
	}

	public List<Contact> getFilteredContacts() {
		return filteredContacts;
	}

	public void setFilteredContacts(List<Contact> filteredContacts) {
		this.filteredContacts = filteredContacts;
	}
	
}
