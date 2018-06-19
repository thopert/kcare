package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;

@Component
@Scope ("view")
public class PasswordEditController implements Serializable {
	static final long serialVersionUID = -7080237677635601852L;
	
	@Autowired
	private UserService userService;
	@Autowired
	private SessionInfoBean sessionInfoBean;
	private User user;
	
	@PostConstruct
	public void init(){
		this.user = sessionInfoBean.getCurrentUser();
	}
	
	public String save(){
		this.userService.saveUser(this.user);
		
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Passwort erfolgreich geändert!"));
		
		return "/secured/common/editPassword.xhtml";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
