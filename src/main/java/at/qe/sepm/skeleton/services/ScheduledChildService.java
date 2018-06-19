package at.qe.sepm.skeleton.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.ScheduledChild;
import at.qe.sepm.skeleton.repositories.ScheduledChildRepository;

/**
 * @author Thomas Perteneder
 *
 */

@Component
@Scope("application")
public class ScheduledChildService {
	@Autowired
	ScheduledChildRepository scheduledChildRepository;
	
	public void addChildToSchedule(Child child, Date date){
		ScheduledChild sc = scheduledChildRepository.getForChildAtDate(date, child.getId());
		 if (sc == null)
             scheduledChildRepository.save(new ScheduledChild(date, child));
		
	}
	
	public void deleteChildFromSchedule(Child child, Date date){
		ScheduledChild sc = scheduledChildRepository.getForChildAtDate(date, child.getId());
		 if (sc != null)
            scheduledChildRepository.delete(sc);
	}
	
	public void addChildrenToSchedule(List<Child> children, Date date){
		for (Child child: children) {
		 ScheduledChild sc = scheduledChildRepository.getForChildAtDate(date, child.getId());
         if (sc == null)
             scheduledChildRepository.save(new ScheduledChild(date, child));
		}
	}
	
	public ScheduledChild loadScheduledChild(Child child, Date date){
		return this.scheduledChildRepository.getForChildAtDate(date, child.getId());
	}
	
	public void removeChildrenToSchedule(List<Child> children, Date date){
		for (Child child: children) {
		 ScheduledChild sc = scheduledChildRepository.getForChildAtDate(date, child.getId());
         if (sc != null)
             scheduledChildRepository.delete(sc);
		}
	}
}
