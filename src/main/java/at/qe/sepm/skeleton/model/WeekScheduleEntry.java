package at.qe.sepm.skeleton.model;

public class WeekScheduleEntry {
	private Child child;
	private boolean days[];
	
	public WeekScheduleEntry(Child child){
		this.child = child;
		this.days = new boolean[5];
	}

	public Child getChild() {
		return child;
	}
	public boolean[] getDays() {
		return days;
	}
	public void setChild(Child child) {
		this.child = child;
	}
	public void setDays(boolean[] days) {
		this.days = days;
	}

}
