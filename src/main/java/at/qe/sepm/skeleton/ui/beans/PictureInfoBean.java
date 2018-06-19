package at.qe.sepm.skeleton.ui.beans;

import java.io.Serializable;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.services.SupervisorService;

@Component
@Scope("session")
public class PictureInfoBean implements Serializable {
	private static final long serialVersionUID = -2219716324651895050L;
	
	@Autowired
	private SessionInfoBean sessionInfoBean;
	@Autowired
	private ParentInfoBean parentInfoBean;
	@Autowired
	private SupervisorService supervisorService;

	public StreamedContent getPicture(){
		if (sessionInfoBean.isLoggedIn()){
			if (this.sessionInfoBean.hasRole("PARENTS")) {
				return this.parentInfoBean.getCurrentParent().getMother().getDisplayPicture();
			} else if (this.sessionInfoBean.hasRole("SUPERVISOR")) {
				return this.supervisorService.getSupervisorByUser(this.sessionInfoBean.getCurrentUser().getId())
						.getPerson().getDisplayPicture();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
