package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Holiday;

import at.qe.sepm.skeleton.repositories.HolidayRepository;

import org.primefaces.model.ScheduleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Service for accessing and manipulating holidays.
 *
 * @author
 */
@Component
@Scope("application")
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    private Date parseDate(String date) {
        Date d = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            d = df.parse(date);
        } catch (Exception e) {
            System.out.println("Unable to parse given date: " + date);
        } finally {
            return d;
        }
    }

    public Holiday createSingleHoliday(String name, String date) {
        Holiday h = new Holiday();
        Date d = parseDate(date);
        h.setFromDate(d);
        h.setToDate(d);
        h.setName(name);

        return h;
    }

    public Holiday createRangedHolidays(String name, String fromdate, String todate) {
        Holiday h = new Holiday();
        h.setFromDate(parseDate(fromdate));
        h.setToDate(parseDate(todate));
        h.setName(name);

        return h;
    }

    public Holiday loadHoliday(String date) {
        Date d = parseDate(date);
        System.out.println("Date: " + d);
        return holidayRepository.getHolidayByDate(d);
    }

    public List<Holiday> loadHolidaysForMonth(int month, int year) {
        return holidayRepository.getHolidaysForMonth(month, year);
    }

    public List<Holiday> loadHolidaysForYear(int year) {
        return holidayRepository.getHolidaysForYear(year);
    }

    public void saveHoliday(Holiday h) {
        holidayRepository.save(h);
    }
    
    public List<Holiday> loadHolidays(){
    	return this.holidayRepository.findAll();
    }
    
    public void saveHoliday(ScheduleEvent sc){
    	Holiday holiday = this.holidayRepository.getById((Long) sc.getData());
    	if (holiday == null){
    		this.holidayRepository.save(new Holiday(sc.getTitle(), sc.getStartDate(), sc.getEndDate()));
    	} else{
    		holiday.setName(sc.getTitle());
    		holiday.setFromDate(sc.getStartDate());
    		holiday.setToDate(sc.getEndDate());
    		this.holidayRepository.save(holiday);
    	}
    }

    public void deleteHoliday(ScheduleEvent sc){
		if (sc != null) {
			Holiday holiday = this.holidayRepository.getById((Long) sc.getData());
			if (holiday != null) {
				this.holidayRepository.delete(holiday);
			}
		}
    }
    

}
