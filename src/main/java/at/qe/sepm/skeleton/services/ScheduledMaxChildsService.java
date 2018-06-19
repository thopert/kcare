package at.qe.sepm.skeleton.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.repositories.ScheduledMaxChildsRepository;

/**
 * @author Thomas Perteneder
 *
 */
@Component
@Scope("application")
public class ScheduledMaxChildsService {
	@Autowired
	private ScheduledMaxChildsRepository scheduledMaxChildsRepository;
	
	
	public Long getMaxChildren(Date date){
		return this.scheduledMaxChildsRepository.getMaxChildrenForDate(date);
	}
}
