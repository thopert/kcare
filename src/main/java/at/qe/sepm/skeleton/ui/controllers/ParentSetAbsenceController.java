package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.services.ChildService;
import at.qe.sepm.skeleton.ui.beans.ParentInfoBean;

/**
 * @author Thomas Perteneder
 *
 */
@Component
@Scope("view")
public class ParentSetAbsenceController implements Serializable {
	
	private static final long serialVersionUID = -3142967442944031305L;

	@Autowired
	private ParentInfoBean parentInfoBean;
	
	@Autowired
	private ChildService childService;
	
	private Date date;
	private Long childId;
	private List<Child> children;
	
	@PostConstruct
	public void init(){
		this.date = new Date();
		this.children = this.parentInfoBean.getCurrentChildren();
		this.childId = this.children.get(0).getId();
	}
	
	public void save(){
		this.childService.setAbsentChild(this.date, this.childId);
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Kind als abwesend markiert!"));
		
	}
	
	public void delete(){
		this.childService.deleteAbsentChild(this.date, this.childId);
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Kind als nicht abwesend markiert!"));
	}
	
	public boolean isAbsent(){
		return this.childService.isAbsentChild(this.date, this.childId);
	}

	public List<Child> getChildren(){
		return this.children;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getChildId() {
		return childId;
	}

	public void setChildId(Long childId) {
		this.childId = childId;
	}

}
