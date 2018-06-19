package at.qe.sepm.skeleton.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static Date currentSimpleDate(){
		Date current;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		current = cal.getTime();
		return current;
	}
	
	public static Date toSimpleDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date current = cal.getTime();
		return current;
	}
	
	public static Date getLunchStart() {
		Calendar cal = Calendar.getInstance();
		int first = cal.get(Calendar.DAY_OF_WEEK);
		
		switch (first) {
		case Calendar.MONDAY:
			cal.add(Calendar.DAY_OF_YEAR, 7);
			break;
		case Calendar.TUESDAY:
			cal.add(Calendar.DAY_OF_YEAR, 6);
			break;
		case Calendar.WEDNESDAY:
			cal.add(Calendar.DAY_OF_YEAR, 5);
			break;
		case Calendar.THURSDAY:
			cal.add(Calendar.DAY_OF_YEAR, 4);
			break;
		case Calendar.FRIDAY:
			if (cal.get(Calendar.HOUR_OF_DAY) >= 12)
				cal.add(Calendar.DAY_OF_YEAR, 10);
			else
				cal.add(Calendar.DAY_OF_YEAR, 3);
			break;
		case Calendar.SATURDAY:
			cal.add(Calendar.DAY_OF_YEAR, 9);
			break;
		case Calendar.SUNDAY:
			cal.add(Calendar.DAY_OF_YEAR, 8);
			break;
		}
		return cal.getTime();
	}
	
	public static Date getLunchEnd() {
		Calendar cal = Calendar.getInstance();
		int first = cal.get(Calendar.DAY_OF_WEEK);

		switch (first) {
		case Calendar.MONDAY:
			cal.add(Calendar.DAY_OF_YEAR, 11);
			break;
		case Calendar.TUESDAY:
			cal.add(Calendar.DAY_OF_YEAR, 10);
			break;
		case Calendar.WEDNESDAY:
			cal.add(Calendar.DAY_OF_YEAR, 9);
			break;
		case Calendar.THURSDAY:
			cal.add(Calendar.DAY_OF_YEAR, 8);
			break;
		case Calendar.FRIDAY:
			if (cal.get(Calendar.HOUR_OF_DAY) >= 12)
				cal.add(Calendar.DAY_OF_YEAR, 14);
			else
				cal.add(Calendar.DAY_OF_YEAR, 7);
			break;
		case Calendar.SATURDAY:
			cal.add(Calendar.DAY_OF_YEAR, 13);
			break;
		case Calendar.SUNDAY:
			cal.add(Calendar.DAY_OF_YEAR, 12);
			break;
		}
		return cal.getTime();
	}
	
	public static Calendar toSimpleCalendar(Calendar date){
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		
		return date;
	}
	
	public static Calendar getWeekStart(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int current = cal.get(Calendar.DAY_OF_WEEK);
		
		switch (current) {
		case Calendar.MONDAY:
			break;
		case Calendar.TUESDAY:
			cal.add(Calendar.DAY_OF_YEAR, -1);
			break;
		case Calendar.WEDNESDAY:
			cal.add(Calendar.DAY_OF_YEAR, -2);
			break;
		case Calendar.THURSDAY:
			cal.add(Calendar.DAY_OF_YEAR, -3);
			break;
		case Calendar.FRIDAY:
			cal.add(Calendar.DAY_OF_YEAR, -4);
			break;
		case Calendar.SATURDAY:
			cal.add(Calendar.DAY_OF_YEAR, -5);
			break;
		case Calendar.SUNDAY:
			cal.add(Calendar.DAY_OF_YEAR, -6);
			break;
		}
		return toSimpleCalendar(cal);
	}
	
	public static Calendar getWeekEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int current = cal.get(Calendar.DAY_OF_WEEK);
		
		switch (current) {
		case Calendar.MONDAY:
			cal.add(Calendar.DAY_OF_YEAR, 6);
			break;
		case Calendar.TUESDAY:
			cal.add(Calendar.DAY_OF_YEAR, 5);
			break;
		case Calendar.WEDNESDAY:
			cal.add(Calendar.DAY_OF_YEAR, 4);
			break;
		case Calendar.THURSDAY:
			cal.add(Calendar.DAY_OF_YEAR, 3);
			break;
		case Calendar.FRIDAY:
			cal.add(Calendar.DAY_OF_YEAR, 2);
			break;
		case Calendar.SATURDAY:
			cal.add(Calendar.DAY_OF_YEAR, 1);
			break;
		case Calendar.SUNDAY:
			break;
		}
		return toSimpleCalendar(cal);
	}
	
	public static Calendar currentSimpleCalendar(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}
}
