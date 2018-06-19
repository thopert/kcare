package at.qe.sepm.skeleton.ui.controllers;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.qe.sepm.skeleton.model.Holiday;
import at.qe.sepm.skeleton.services.HolidayService;

/**
 * @author Daniel Jäger
 *
 */
@Component
@Scope("view")
public class HolidayController {

	@Autowired
	private HolidayService holidayService;

	private Holiday holiday;

	@PostConstruct
	public void init(){
		this.holiday = new Holiday();
	}

	public String save(){
		this.holidayService.saveHoliday(this.holiday);
		FacesContext.getCurrentInstance().addMessage("success",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Feiertag erfolgreich erstellt!"));
		
		return "/secured/welcome.xhtml";
	}
	
	public Holiday getHoliday() {
		return this.holiday;
	}

	public void setHoliday(Holiday holiday) {
		this.holiday = holiday;
	}

}
