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
import at.qe.sepm.skeleton.services.ChildService;
import at.qe.sepm.skeleton.services.ParentService;

/**
 * @author Thomas Perteneder
 *
 */
@Component
@Scope("view")
public class ChildCreateController implements Serializable {
	private static final long serialVersionUID = 5314177432647113557L;

	private Child child;

	@Autowired
	private ChildService childService;

	@Autowired
	private ParentService parentService;

	private List<Parent> parents;
	private Long parentId;

	@PostConstruct
	public void init() {
		child = this.childService.getEmptyChild();
		this.parents = this.parentService.loadParents();
	}

	public String createChild() {
		if (this.parentId != null) {
			Parent parent = this.parentService.loadParent(parentId);
			this.child.setParents(parent);
		}
		
		this.childService.saveChild(child);
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Kind erfolgreich erstellt!"));
		return "/secured/supervisor/dataManagement/createChild.xhtml";
	}

	public Child getChild() {
		return child;
	}

	public void setChild(Child child) {
		this.child = child;
	}

	public List<Parent> getParents() {
		return parents;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public void handleFileUpload(FileUploadEvent event){
		this.child.getPerson().setPicture(event.getFile().getContents());
	}

}
