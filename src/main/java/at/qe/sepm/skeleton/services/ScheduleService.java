package at.qe.sepm.skeleton.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Child;
import at.qe.sepm.skeleton.model.ContactPickup;
import at.qe.sepm.skeleton.model.MonthScheduleEntry;
import at.qe.sepm.skeleton.model.Schedule;
import at.qe.sepm.skeleton.model.ScheduleEntry;
import at.qe.sepm.skeleton.model.ScheduledAbsentChild;
import at.qe.sepm.skeleton.model.ScheduledChild;
import at.qe.sepm.skeleton.model.ScheduledMaxChilds;
import at.qe.sepm.skeleton.model.ScheduledSupervisor;
import at.qe.sepm.skeleton.model.Supervisor;
import at.qe.sepm.skeleton.model.WeekScheduleEntry;
import at.qe.sepm.skeleton.repositories.ChildRepository;
import at.qe.sepm.skeleton.repositories.ContactPickupRepository;
import at.qe.sepm.skeleton.repositories.ScheduledAbsentChildRepository;
import at.qe.sepm.skeleton.repositories.ScheduledChildRepository;
import at.qe.sepm.skeleton.repositories.ScheduledMaxChildsRepository;
import at.qe.sepm.skeleton.repositories.ScheduledSupervisorRepository;
import at.qe.sepm.skeleton.utils.DateUtils;

/**
 * Service for accessing and manipulating schedules.
 * @author Dominik Kuen (csat2284)
 */
@Component
@Scope("application")
public class ScheduleService {

    @Autowired
    private ScheduledSupervisorRepository scheduledSupervisorRepository;

    @Autowired
    private ScheduledChildRepository scheduledChildRepository;

    @Autowired
    private ScheduledAbsentChildRepository scheduledAbsentChildRepository;

    @Autowired
    private ScheduledMaxChildsRepository scheduledMaxChildsRepository;
    
    @Autowired
    private ContactPickupRepository contactPickupRepository;
    
    @Autowired
    private ChildRepository childRepository;

    public Schedule loadSchedule(Date date) {
        Schedule s = new Schedule();
        s.setDate(date);
        s.setSupervisors(new HashSet<>(scheduledSupervisorRepository.getSupervisorsForDate(date)));
        s.setChilds(new HashSet<>(scheduledChildRepository.getChildrenForDate(date)));
        s.setAbsentChilds(new HashSet<>(scheduledAbsentChildRepository.getChildrenForDate(date)));
        s.setMaxChilds(scheduledMaxChildsRepository.getMaxChildrenForDate(date));
        return s;
    }

    public Schedule loadSchedule(String date) {
        Schedule s = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            s = loadSchedule(df.parse(date));
        } catch (Exception e) {
            System.out.println("Unable to parse given date: " + date);
        } finally {
        	return s;
        }
	}

    public void saveSchedule(Schedule s) {
        // check for new supervisors, children, absent children and max-child-limits
        for (Supervisor sv : s.getSupervisors()) {
            ScheduledSupervisor ssv = scheduledSupervisorRepository.getForSupervisorAtDate(s.getDate(), sv.getId());
            if (ssv == null)
                scheduledSupervisorRepository.save(new ScheduledSupervisor(s.getDate(), sv));
        }

        for (Child c : s.getChilds()) {
            ScheduledChild sc = scheduledChildRepository.getForChildAtDate(s.getDate(), c.getId());
            if (sc == null)
                scheduledChildRepository.save(new ScheduledChild(s.getDate(), c));
        }

        for (Child c : s.getAbsentChilds()) {
            ScheduledAbsentChild sac = scheduledAbsentChildRepository.getForAbsentChildAtDate(s.getDate(), c.getId());
            if (sac == null)
                scheduledAbsentChildRepository.save(new ScheduledAbsentChild(s.getDate(), c));
        }

        ScheduledMaxChilds smc = scheduledMaxChildsRepository.getForDate(s.getDate());
        if (smc == null)
            scheduledMaxChildsRepository.save(new ScheduledMaxChilds(s.getDate(), s.getMaxChilds()));
        else {
        	smc.setChildsLimit(s.getMaxChilds());
        	scheduledMaxChildsRepository.save(smc);
        }

        // check for removed supervisors, children, absent children
        List<Supervisor> svl = scheduledSupervisorRepository.getSupervisorsForDate(s.getDate());
        for (Supervisor sv : svl) {
            if (!s.getSupervisors().contains(sv))
                scheduledSupervisorRepository.delete(scheduledSupervisorRepository.getForSupervisorAtDate(s.getDate(), sv.getId()));
        }

        List<Child> cl = scheduledChildRepository.getChildrenForDate(s.getDate());
        for (Child c : cl) {
            if (!s.getChilds().contains(c))
                scheduledChildRepository.delete(scheduledChildRepository.getForChildAtDate(s.getDate(), c.getId()));
        }

        List<Child> acl = scheduledAbsentChildRepository.getChildrenForDate(s.getDate());
        for (Child c : acl) {
            if (!s.getAbsentChilds().contains(c))
                scheduledAbsentChildRepository.delete(scheduledAbsentChildRepository.getForAbsentChildAtDate(s.getDate(), c.getId()));
        }
    }
    
    public List<ScheduleEntry> getScheduleEntries(Date date){
    	if (date == null)
    		return null;
    	
    	List<Child> here = this.scheduledChildRepository.getChildrenForDate(date);
    	List<Child> absent = this.scheduledAbsentChildRepository.getChildrenForDate(date);
    	List<ScheduleEntry> ses = new ArrayList<ScheduleEntry>();
    	
    	for(Child c: here){
    		ScheduleEntry se = new ScheduleEntry();
    		se.setChild(c);
    		se.setBirthday(c.getPerson().getBirthDate() == date);
    		
    		if (absent != null)
    			se.setAbsent(absent.contains(c));
    		else
    			se.setAbsent(false);
    		
    		ContactPickup cp = this.contactPickupRepository.getByDateChild(date, c.getId());
    		
    		if (cp != null)
    			se.setPickup(cp.getContact());
    		
    		ses.add(se);
    	}
    	
    	return ses;
    	
    }
    
    public List<WeekScheduleEntry> getWeekScheduleEntries(Date date){
    	Calendar start = DateUtils.getWeekStart(date);
    	Calendar end = DateUtils.getWeekEnd(date);
    	
		Set<Child> children = this.scheduledChildRepository.getByInterval(start.getTime(), end.getTime());
		List<WeekScheduleEntry> wses = new ArrayList<>();
		for (Child c : children) {
			WeekScheduleEntry wse = new WeekScheduleEntry(c);
			for (int i = 0; i < 5; i++, start.add(Calendar.DAY_OF_YEAR, 1)) {
				if (this.scheduledChildRepository.getForChildAtDate(start.getTime(), c.getId()) != null)
					wse.getDays()[i] = true;
				else
					wse.getDays()[i] = false;
			}
			wses.add(wse);
			start.add(Calendar.DAY_OF_YEAR, -5);
		}
    	return wses;
    	
    }
    
    public List<MonthScheduleEntry> getMonthScheduleEntries (Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		List<MonthScheduleEntry> mses = new ArrayList<MonthScheduleEntry>();
		for (Child c : this.childRepository.findAll()) {
			int daysCount = 0;
			for (int i = 0; i < daysInMonth; i++, cal.add(Calendar.DAY_OF_MONTH, 1)) {
				if (this.scheduledChildRepository.getForChildAtDate(cal.getTime(), c.getId()) != null){
					daysCount++;
				}
			}
			if (daysCount != 0){
				mses.add(new MonthScheduleEntry(c, daysCount));
			}
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}

		if (mses.isEmpty())
			return null;
		else
			return mses;
	}

}
