package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.MonthScheduleEntry;
import at.qe.sepm.skeleton.services.ScheduleService;
import at.qe.sepm.skeleton.utils.DateUtils;

@Component
@Scope("view")
public class MonthScheduleController implements Serializable{
	private static final long serialVersionUID = 3253753631481659392L;

	@Autowired
	private ScheduleService scheduleService;
	private Date date;
	private List<MonthScheduleEntry> monthScheduleEntries;
	private List<MonthScheduleEntry> filteredMonthScheduleEntries;

	@PostConstruct
	public void init(){
		Calendar cal = DateUtils.currentSimpleCalendar();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		this.date = cal.getTime();
		this.updateSchedule();
	}

	public void next(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.date);
		cal.add(Calendar.MONTH, 1);
		this.date = cal.getTime();
		this.updateSchedule();
	}

	public void previous(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.date);
		cal.add(Calendar.MONTH, -1);
		this.date = cal.getTime();
		this.updateSchedule();
	}

	private void updateSchedule(){
		this.monthScheduleEntries = this.scheduleService.getMonthScheduleEntries(this.date);
	}

	public Date getStart(){
		return this.date;
	}

	public Date getEnd(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	public int getTotal(){
		int total = 0;
		if (this.monthScheduleEntries != null){
			for (MonthScheduleEntry mse : this.monthScheduleEntries)
				total += mse.getDaysCount();
		}
		return total;
	}

	public Date getDate() {
		return date;
	}

	public List<MonthScheduleEntry> getMonthScheduleEntries() {
		return monthScheduleEntries;
	}

	public List<MonthScheduleEntry> getFilteredMonthScheduleEntries() {
		return filteredMonthScheduleEntries;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setMonthScheduleEntries(List<MonthScheduleEntry> monthScheduleEntries) {
		this.monthScheduleEntries = monthScheduleEntries;
	}

	public void setFilteredMonthScheduleEntries(List<MonthScheduleEntry> filteredMonthScheduleEntries) {
		this.filteredMonthScheduleEntries = filteredMonthScheduleEntries;
	}

}
