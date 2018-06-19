package at.qe.sepm.skeleton.ui.beans;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Parent;
import at.qe.sepm.skeleton.model.Supervisor;
import at.qe.sepm.skeleton.model.Person;
import at.qe.sepm.skeleton.services.SupervisorService;

@Component
@Scope("session")
public class PersonInfoBean implements Serializable {
	private static final long serialVersionUID = -2219716324651895050L;

	@Autowired
	private SessionInfoBean sessionInfoBean;
	@Autowired
	private ParentInfoBean parentInfoBean;
	@Autowired
	private SupervisorService supervisorService;

	public String getFirstName() {
		if (sessionInfoBean.isLoggedIn()){
			if (this.sessionInfoBean.hasRole("ADMIN")) {
				return "Admin";
			} else if (this.sessionInfoBean.hasRole("PARENTS")) {
				Parent par = this.parentInfoBean.getCurrentParent();
				Person mother = par.getMother();
				Person father = par.getFather();
				return mother.getFirstName() + " und " + father.getFirstName();
			} else if (this.sessionInfoBean.hasRole("SUPERVISOR")) {
				Person per = this.supervisorService.getSupervisorByUser(this.sessionInfoBean.getCurrentUser().getId()).getPerson();
				return per.getFirstName();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
