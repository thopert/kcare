package at.qe.sepm.skeleton.ui.beans;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.Parent;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.UserRole;
import at.qe.sepm.skeleton.services.ParentService;


/**
 * @author Thomas Perteneder
 *
 */
@Component
@Scope("session")
public class ParentInfoBean {
	@Autowired
	SessionInfoBean sessionInfoBean;
	
	@Autowired
	ParentService parentService;
	
	public Parent getCurrentParent(){
		User user = this.sessionInfoBean.getCurrentUser();
		if (user.hasRole(UserRole.PARENTS))
			return this.parentService.loadParentByUser(user);
		
		return null;
		
	}
	public List<Child> getCurrentChildren(){
		Parent parent = this.getCurrentParent();
		if (parent != null)
			return this.parentService.loadChildrenByParent(parent);
		return null;
		
	}

}
