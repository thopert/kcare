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
import at.qe.sepm.skeleton.services.LunchService;
import at.qe.sepm.skeleton.ui.beans.ParentInfoBean;
import at.qe.sepm.skeleton.utils.DateUtils;

/**
 * @author Dominik Kuen, Thomas Perteneder (mods)
 *
 */
@Component
@Scope("view")
public class ParentSetLunchController implements Serializable {
	private static final long serialVersionUID = -4869004455841296862L;

	@Autowired
	private LunchService lunchService;

	@Autowired
	private ParentInfoBean parentInfoBean;

	private Long childId;
	private Date date;

	@PostConstruct
	public void init() {
		this.date = DateUtils.toSimpleDate(DateUtils.getLunchStart());
		this.childId = this.parentInfoBean.getCurrentChildren().get(0).getId();
	}

	public void save() {
		this.lunchService.saveLunch(this.childId, this.date);
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Mittagessen erfolgreich zugeteilt!"));

	}

	public void delete() {
		this.lunchService.deleteLunch(this.childId, this.date);
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Mittagessen erfolgreich entfernt!"));

	}

	public List<Child> getChildren() {
		return this.parentInfoBean.getCurrentChildren();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean hasLunch() {
		return this.lunchService.hasLunch(this.childId, this.date);
	}

	public Long getChildId() {
		return childId;
	}

	public void setChildId(Long childId) {
		this.childId = childId;
	}
	
	public Date getLunchStart(){
		return DateUtils.getLunchStart();
	}
	
	public Date getLunchEnd(){
		return DateUtils.getLunchEnd();
	}

}
