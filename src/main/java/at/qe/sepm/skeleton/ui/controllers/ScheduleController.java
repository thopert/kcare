package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.ScheduleEntry;
import at.qe.sepm.skeleton.services.ScheduleService;
import at.qe.sepm.skeleton.utils.DateUtils;

/**
 * @author Thomas Perteneder
 *
 */
@Component
@Scope("view")
public class ScheduleController implements Serializable {
	private static final long serialVersionUID = 4370202465284278198L;

	@Autowired
	private ScheduleService scheduleService;

	private Date date;
	
	private List<ScheduleEntry> scheduleEntries;
	private List<ScheduleEntry> filteredScheduleEntries;

	@PostConstruct
	public void init() {
		this.date = DateUtils.toSimpleDate(new Date());
		this.scheduleEntries = this.scheduleService.getScheduleEntries(this.date);
	}

	public List<ScheduleEntry> getScheduleEntries() {
		return this.scheduleEntries;
	}

	public void update(){
		if (this.date != null)
		this.scheduleEntries = this.scheduleService.getScheduleEntries(this.date);
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<ScheduleEntry> getFilteredScheduleEntries() {
		return filteredScheduleEntries;
	}

	public void setFilteredScheduleEntries(List<ScheduleEntry> filteredScheduleEntries) {
		this.filteredScheduleEntries = filteredScheduleEntries;
	}

}
