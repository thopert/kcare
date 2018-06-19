package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Parent;
import at.qe.sepm.skeleton.services.ParentService;


/**
 * @author Thomas Perteneder
 *
 */
@Component
@Scope("view")
public class ParentListController implements Serializable{
	private static final long serialVersionUID = -590266549437191127L;
	
	@Autowired
	private ParentService parentService;
	
	private List<Parent> parents;
	private List<Parent> filteredParents;
	
	@PostConstruct
	public void init(){
		this.parents = this.parentService.loadParents();
	}
	
	public List<Parent> getParents(){
		return this.parents;
	}

	public List<Parent> getFilteredParents() {
		return filteredParents;
	}

	public void setFilteredParents(List<Parent> filteredParents) {
		this.filteredParents = filteredParents;
	}
	
}
