package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.UserService;

/**
 * Controller for the user list view.
 *
 * This class is part of the skeleton project provided for students of the
 * course "Softwaredevelopment and Project Management" offered by the University
 * of Innsbruck.
 */
@Component
@Scope("view")
public class UserListController implements Serializable{
	private static final long serialVersionUID = -931371606170981229L;

	@Autowired
    private UserService userService;
    
    private Collection<User> users;

    @PostConstruct
    public void init(){
    	this.users = this.userService.getAllUsers();
    }
    
    /**
     * Returns a list of all users.
     *
     * @return
     */
    public Collection<User> getUsers() {
       return this.users;
    }

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

}
