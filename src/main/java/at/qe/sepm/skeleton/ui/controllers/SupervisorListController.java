package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Supervisor;
import at.qe.sepm.skeleton.services.SupervisorService;

/**
 * @author Dominik Kuen
 *
 */
@Component
@Scope("view")
public class SupervisorListController implements Serializable {
	private static final long serialVersionUID = 825701170440944820L;

	@Autowired
	private SupervisorService supervisorService;
	
	private List<Supervisor> supervisors;

	@PostConstruct
	public void init(){
		this.supervisors = this.supervisorService.loadSupervisors();
	}
	
	public List<Supervisor> getSupervisors() {
		return this.supervisors;
	}

	public void setSupervisors(List<Supervisor> supervisors) {
		this.supervisors = supervisors;
	}
	
	

}
