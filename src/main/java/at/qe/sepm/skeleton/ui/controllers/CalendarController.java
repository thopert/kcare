package at.qe.sepm.skeleton.ui.controllers;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Holiday;
import at.qe.sepm.skeleton.services.HolidayService;

@Component
@Scope("view")
public class CalendarController implements Serializable {
	private static final long serialVersionUID = 1267825578156162850L;

	@Autowired
	private HolidayService holidayService;
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private boolean showDelete;

	@PostConstruct
	public void init() {
		this.eventModel = new DefaultScheduleModel();
		for (Holiday h : this.holidayService.loadHolidays())
			this.eventModel.addEvent(new DefaultScheduleEvent(h.getName(), h.getFromDate(), h.getToDate(), h.getId()));
	}

	public void addEvent(ActionEvent actionEvent) {
		if (this.event.getId() == null) {
			Holiday holiday = new Holiday(this.event.getTitle(), this.event.getStartDate(), this.event.getEndDate());
			this.holidayService.saveHoliday(holiday);
			this.event = new DefaultScheduleEvent(this.event.getTitle(), this.event.getStartDate(), this.event.getEndDate(), holiday.getId());
			this.eventModel.addEvent(this.event);
		} else {
			this.eventModel.updateEvent(this.event);
			this.holidayService.saveHoliday(this.event);
		}
	}

	public void deleteEvent(ActionEvent actionEvent) {
		if (this.event.getId() != null) {
			this.eventModel.deleteEvent(this.event);
			this.holidayService.deleteHoliday(this.event);
		}
		this.event = new DefaultScheduleEvent();
	}

	public void onEventSelect(SelectEvent selectEvent) {
		this.event = (ScheduleEvent) selectEvent.getObject();
		this.showDelete = true;
	}

	public void onDateSelect(SelectEvent selectEvent) {
		this.event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
		this.showDelete = false;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public boolean isShowDelete() {
		return showDelete;
	}

	public void setShowDelete(boolean showDelete) {
		this.showDelete = showDelete;
	}

}
