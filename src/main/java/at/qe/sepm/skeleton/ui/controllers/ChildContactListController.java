package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Person;
import at.qe.sepm.skeleton.services.ChildService;
import at.qe.sepm.skeleton.services.ContactService;

@Component
@Scope ("view")
public class ChildContactListController implements Serializable {
	private static final long serialVersionUID = 6738820762508020267L;

	@Autowired
	private ChildService childService;
	
	@Autowired
	private ContactService contactService;
	
	private Long childId;
	private List<Child> children;
	private boolean showAll;
	
	@PostConstruct
	public void init(){
		this.children = this.childService.loadChildren();
		Collections.sort(this.children);
		this.showAll = true;
	}
	
	public void modeChanged(){
		if (this.childId == null)
			showAll= true;
		else
			showAll = false;
	}
	
	public List<Person> getContactList(){
		return this.childService.getContactList(this.childId);
	}
	
	public List<Person> getContactList(Child child){
		return this.childService.getContactList(child.getId());
	}
	
	public boolean isContact(Person person){
		return this.contactService.isContact(person);
	}
	
	public List<Child> getChildren(){
		return this.children;
	}

	public Long getChildId() {
		return childId;
	}

	public Child getChild(){
		return this.childService.loadChild(this.childId);
	}
	
	public void setChildId(Long childId) {
			this.childId = childId;
	}

	public boolean isShowAll() {
		return showAll;
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}
	
}
