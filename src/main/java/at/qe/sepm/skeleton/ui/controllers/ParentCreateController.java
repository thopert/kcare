package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Parent;
import at.qe.sepm.skeleton.model.Person;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.UserRole;
import at.qe.sepm.skeleton.services.ChildService;
import at.qe.sepm.skeleton.services.ParentService;

/**
 * @author Thomas Perteneder
 *
 */
@Component
@Scope("view")
public class ParentCreateController implements Serializable {
	private static final long serialVersionUID = -5288942336725197162L;

	private Parent parent;
	
	@Autowired
	private ParentService parentService;
	
	@Autowired
	private ChildService childService;
	private List<Child> freeChildren;
	private List<String> selectedChildren;

	@PostConstruct
	public void init() {
		this.parent = new Parent();
		this.parent.setActive(true);

		User user = new User();
		String rpw = User.getRandomPassword();
		user.setPassword(rpw);
		user.setRole(UserRole.PARENTS);
		
		Person mother = new Person();
		mother.setSex(true);
		
		Person father = new Person();
		father.setSex(false);

		this.parent.setUser(user);
		this.parent.setMother(mother);
		this.parent.setFather(father);

		this.freeChildren = this.childService.loadChildrenNoParents();
	}

	public String createParent() {
		Long id;
		Child child;
		
		for(String idString: this.selectedChildren){
			id = Long.parseLong(idString, 10);
			child = this.childService.loadChild(id);
			child.setParents(this.parent);
//			this.parent.setChild(child);
		}
		
		this.parentService.saveNewParent(parent);
		
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Eltern erfolgreich erstellt!"));
		
		return "/secured/supervisor/dataManagement/createParent.xhtml";
		
	}
	
	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public List<Child> getFreeChildren() {
		return freeChildren;
	}

	public List<String> getSelectedChildren() {
		return selectedChildren;
	}

	public void setFreeChildren(List<Child> children) {
		this.freeChildren = children;
	}

	public void setSelectedChildren(List<String> selectedChildren) {
		this.selectedChildren = selectedChildren;
	}
	
	public void uploadMother(FileUploadEvent event){
		this.parent.getMother().setPicture(event.getFile().getContents());
	}

	public void uploadFather(FileUploadEvent event){
		this.parent.getFather().setPicture(event.getFile().getContents());
	}
}
