package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Schedule;
import at.qe.sepm.skeleton.model.Supervisor;
import at.qe.sepm.skeleton.services.ChildService;
import at.qe.sepm.skeleton.services.ScheduleService;
import at.qe.sepm.skeleton.services.ScheduledMaxChildsService;
import at.qe.sepm.skeleton.services.SupervisorService;
import at.qe.sepm.skeleton.utils.DateUtils;
import at.qe.sepm.skeleton.utils.PickListHelper;

@Component
@Scope("session")
public class SetupScheduleController implements Serializable {
	private static final long serialVersionUID = 9025148404551694490L;

	@Autowired
	private ChildService childService;
	
	@Autowired
	private ScheduledMaxChildsService scheduledMaxChildsService;
	
	private Long maxChildren;
	
	@Autowired
	private SupervisorService supervisorService;
	
	@Autowired
	private ScheduleService scheduleService;
	
	private DualListModel<String> children;
	
	private DualListModel<String> supervisors;

	private Date date;

	@PostConstruct
	public void init() {
		this.date = DateUtils.toSimpleDate(new Date());
	}

	public void setupChildList() {
		List<Child> source = this.childService.loadChildrenNotForDate(this.date);
		List<Child> target = this.childService.loadChildrenForDate(this.date);
		this.children = PickListHelper.getChildPickList(source, target);
	}
	
	public void setupSupervisorList(){
		List <Supervisor> source = this.supervisorService.loadSupervisorNotForDate(this.date);
		List <Supervisor> target = this.supervisorService.loadSupervisorForDate(this.date);
		this.supervisors = PickListHelper.getSupervisorPickList(source, target);
	}

	public String save() {
		this.children.getSource();
		
		Schedule schedule = this.scheduleService.loadSchedule(this.date);
		
		schedule.setMaxChilds(this.maxChildren);
		
		List<Child> sourceChildren = this.childService.loadFirstChildren(this.children.getSource());
		for (Child c:sourceChildren)
			schedule.removeChild(c);
		
		List<Child> targetChildren = this.childService.loadFirstChildren(this.children.getTarget());
			schedule.addChildren(targetChildren);
		
		List<Supervisor> sourceSupervisors = this.supervisorService.loadFirstSupervisors(this.supervisors.getSource());
		for (Supervisor s: sourceSupervisors)
			schedule.removeSupervisor(s);
		
		List<Supervisor> targetSupervisors = this.supervisorService.loadFirstSupervisors(this.supervisors.getTarget());
			schedule.addSupervisors(targetSupervisors);
		
		this.scheduleService.saveSchedule(schedule);

		if(targetChildren != null && this.maxChildren !=null)
			this.displayInfo(targetChildren.size() > this.maxChildren);
		else
			this.displayInfo(false);
		
		return "/secured/supervisor/schedule/setupSchedule.xhtml";
		
	}
	
	public void displayInfo(boolean overCapacity){
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Schedule erfolgreich modifiziert!"));

		if (overCapacity)
			FacesContext.getCurrentInstance().addMessage("maxChildren",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Warnung: Maximalbelegung überschritten!", ""));
	}


	public DualListModel<String> getChildren() {
		setupChildList();
		return children;
	}
	
	public DualListModel<String> getSupervisors() {
		setupSupervisorList();
		return this.supervisors;
	}
	
	public Long getMaxChildren(){
		return this.scheduledMaxChildsService.getMaxChildren(this.date);
	}
	
	public void setMaxChildren(Long maxChildren){
		this.maxChildren = maxChildren;
	}

	public void setChildren(DualListModel<String> children) {
		this.children = children;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setSupervisors(DualListModel<String> supervisors) {
		this.supervisors = supervisors;
	}
	
}
