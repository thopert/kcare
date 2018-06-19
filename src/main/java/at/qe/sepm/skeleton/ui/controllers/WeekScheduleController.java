package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.WeekScheduleEntry;
import at.qe.sepm.skeleton.services.ScheduleService;
import at.qe.sepm.skeleton.utils.DateUtils;

@Component
@Scope("view")
public class WeekScheduleController implements Serializable {
	private static final long serialVersionUID = -6807455714310880993L;
	
	@Autowired
	private ScheduleService scheduleService;
	private Date date;
	private List<WeekScheduleEntry> weekScheduleEntries;
	private List<WeekScheduleEntry> filteredWeekScheduleEntries;
	
	@PostConstruct
	public void init(){
		this.date = DateUtils.currentSimpleDate();
		this.weekScheduleEntries = this.scheduleService.getWeekScheduleEntries(this.date);
	}
	
	public List<WeekScheduleEntry> getWeekScheduleEntries(){
		return this.weekScheduleEntries;
		
	}
	
	public void nextWeek(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.date);
		cal.add(Calendar.DAY_OF_YEAR, 7);
		this.date = cal.getTime();
		this.weekScheduleEntries = this.scheduleService.getWeekScheduleEntries(this.date);
	}
	
	public void previousWeek(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.date);
		cal.add(Calendar.DAY_OF_YEAR, -7);
		this.date = cal.getTime();
		this.weekScheduleEntries = this.scheduleService.getWeekScheduleEntries(this.date);
	}
	
	public Date getWeekStart(){
		return DateUtils.getWeekStart(this.date).getTime();
	}
	
	public Date getWeekEnd(){
		return DateUtils.getWeekEnd(this.date).getTime();
	}

	public List<WeekScheduleEntry> getFilteredWeekScheduleEntries() {
		return filteredWeekScheduleEntries;
	}

	public void setFilteredWeekScheduleEntries(List<WeekScheduleEntry> filteredWeekScheduleEntries) {
		this.filteredWeekScheduleEntries = filteredWeekScheduleEntries;
	}
	
}
