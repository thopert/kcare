package at.qe.sepm.skeleton.ui.controllers;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.UserRole;
import at.qe.sepm.skeleton.services.UserService;


@Component
@Scope("session")
public class UserDetailController {

    @Autowired
    private UserService userService;

    private User user;

    public String save() {
        this.userService.saveUser(this.user);
        FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", this.user + " erfolgreich modifiziert!"));
		return "/admin/editUser.xhtml";
       
    }

    public String delete() {
        this.userService.deleteUser(this.user);
        FacesContext.getCurrentInstance().addMessage("success",
    				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", this.user + " erfolgreich gelöscht!"));
        
        return "/admin/users.xhtml";
    		
    }
    
    public void setUser(User user) {
        this.user = user;
 
    }
  
    public User getUser() {
        return user;
    }
    
    public List<UserRole> getFreeRoles(){
    	return this.userService.getFreeRoles(this.user.getRole());
    }

}
