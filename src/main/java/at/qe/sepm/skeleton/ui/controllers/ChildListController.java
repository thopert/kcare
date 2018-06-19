package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.services.ChildService;
import at.qe.sepm.skeleton.ui.beans.ParentInfoBean;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;

/**
 * @author Thomas Perteneder
 *
 */
@Component
@Scope("view")
public class ChildListController implements Serializable {
	private static final long serialVersionUID = -3230673234804230584L;
	
	@Autowired
	private ChildService childService;
	@Autowired
	private SessionInfoBean sessionInfoBean;
	@Autowired
	private ParentInfoBean parentInfoBean;
	
	private List<Child> children;
	private List<Child> filteredChildren;
	
	@PostConstruct
	private void init(){
		if (this.sessionInfoBean.hasRole("PARENTS"))
			this.children = this.parentInfoBean.getCurrentChildren();
		else
			this.children = this.childService.loadChildren();
	}

	public List<Child> getChildren() {
		return this.children;
	}

	public List<Child> getFilteredChildren() {
		return filteredChildren;
	}

	public void setFilteredChildren(List<Child> filteredChildren) {
		this.filteredChildren = filteredChildren;
	}
	
}
